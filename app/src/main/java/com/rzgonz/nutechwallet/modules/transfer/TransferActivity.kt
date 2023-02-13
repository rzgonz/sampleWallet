package com.rzgonz.nutechwallet.modules.transfer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rzgonz.nutechwallet.R
import com.rzgonz.nutechwallet.componen.SampleComposeTheme
import com.rzgonz.nutechwallet.core.Success
import com.rzgonz.nutechwallet.core.utils.decimalFormat
import com.rzgonz.nutechwallet.core.utils.orZero
import com.rzgonz.nutechwallet.core.utils.safeLaunchWithResult
import com.rzgonz.nutechwallet.core.utils.showToastMessage
import org.koin.android.ext.android.inject

class TransferActivity : AppCompatActivity() {
    private val transferViewModel: TransferViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleComposeTheme {
                TransferScreen(transferViewModel.state.collectAsState())
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun TransferScreen(state: State<TransferState>) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.payment_title_transfer_screen)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            finish()
                        }) {
                            Icon(Icons.Filled.ArrowBack, "backIcon")
                        }
                    },
                )
            }, content = {
                TransferContainer(state = state)
            }
        )
    }

    @Composable
    private fun TransferContainer(state: State<TransferState>) {
        val inputAmount = remember { mutableStateOf(TextFieldValue()) }
        val amountInputError = remember { mutableStateOf(Exception()) }

        if (state.value.transferAsync is Success) {
            showToastMessage("Transfer Success")
            finish()
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text =
                stringResource(
                    id = R.string.home_text_saldo
                ), color =
                Color.Black, fontSize = 18.sp
            )

            Text(
                text = stringResource(
                    id = R.string.home_text_balance,
                    state.value.balanceAsync.invoke()?.balance.orZero().decimalFormat()
                ),
                color =
                Color.Black,
                fontSize = 21.sp
            )
            TextField(
                value = inputAmount.value,
                onValueChange = {
                    inputAmount.value = it
                },
                placeholder = {
                    Text(
                        text = stringResource(
                            id = R.string.payment_text_max_transfer,
                            state.value.balanceAsync.invoke()?.balance.orZero()
                        )
                    )
                },
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Number,
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    // below line is used to add font
                    // size for our text field
                    fontSize = 15.sp,

                    // below line is use to change font family.
                    fontFamily = FontFamily.SansSerif
                ),
                trailingIcon = {
                    if (amountInputError.value.message.isNullOrEmpty().not())
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                }
            )
            if (amountInputError.value.message.isNullOrEmpty().not()) {
                Text(
                    text = amountInputError.value.message.orEmpty(),
                    color = Color.Red,
                    modifier = Modifier
                )
            }

            Button(
                onClick = {
                    amountInputError.value = Exception()
                    val amount = safeLaunchWithResult(0) {
                        inputAmount.value.text.toInt()
                    }
                    if (state.value.balanceAsync.invoke()?.balance.orZero() > inputAmount.value.text.toDouble()) {
                        transferViewModel.transfer(amount)
                    } else {
                        amountInputError.value =
                            Exception(baseContext.getString(R.string.payment_title_saldo_not_enough))
                    }

                },
                enabled = state.value.balanceAsync is Success
            ) {
                Text(text = stringResource(R.string.common_button_submit))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        transferViewModel.getBalance()
    }

    @SuppressLint("UnrememberedMutableState")
    @Preview(name = "TopUp Screen", showBackground = true, showSystemUi = true)
    @Composable
    private fun DefaultPreview() {
        TransferScreen(mutableStateOf(TransferState()))
    }
}