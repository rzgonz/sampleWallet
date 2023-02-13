package com.rzgonz.nutechwallet.modules.topup

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rzgonz.nutechwallet.componen.SampleComposeTheme
import com.rzgonz.nutechwallet.core.Success
import com.rzgonz.nutechwallet.core.utils.safeLaunchWithResult
import com.rzgonz.nutechwallet.core.utils.showToastMessage
import org.koin.android.ext.android.inject

class TopUpActivity : AppCompatActivity() {
    private val topUpViewModel: TopUpViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleComposeTheme {
                TopUpsScreen(topUpViewModel.state.collectAsState())
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun TopUpsScreen(state: State<TopUpState>) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "TopUp Screen") },
                    navigationIcon = {
                        IconButton(onClick = {
                            finish()
                        }) {
                            Icon(Icons.Filled.ArrowBack, "backIcon")
                        }
                    },
                )
            }, content = {
                TopUpForm(state = state)
            }
        )
    }

    @Composable
    private fun TopUpForm(state: State<TopUpState>) {
        val inputAmount = remember { mutableStateOf(TextFieldValue()) }
        val amountInputError = remember { mutableStateOf(Exception()) }

        if (state.value.topUpAsync is Success) {
            showToastMessage("TopUp Success")
            finish()
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = inputAmount.value,
                onValueChange = {
                    inputAmount.value = it
                },
                placeholder = { Text(text = "Min TopUp 10.000") },
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

            Button(onClick = {
                amountInputError.value = Exception()
                val amount = safeLaunchWithResult(0) {
                    inputAmount.value.text.toInt()
                }
                if (amount < 10000) {
                    amountInputError.value = Exception("Min TopUp 10.000")
                } else {
                    topUpViewModel.topUp(amount)
                }

            }) {
                Text(text = "Submit")
            }

        }
    }


    @SuppressLint("UnrememberedMutableState")
    @Preview(name = "TopUp Screen", showBackground = true, showSystemUi = true)
    @Composable
    private fun DefaultPreview() {
        TopUpsScreen(mutableStateOf(TopUpState()))
    }
}