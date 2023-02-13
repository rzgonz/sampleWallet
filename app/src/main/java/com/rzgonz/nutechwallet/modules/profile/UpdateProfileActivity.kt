package com.rzgonz.nutechwallet.modules.profile

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
import com.rzgonz.nutechwallet.core.Fail
import com.rzgonz.nutechwallet.core.Success
import com.rzgonz.nutechwallet.core.utils.showToastMessage
import com.rzgonz.nutechwallet.data.dto.UpdateProfileDto
import org.koin.android.ext.android.inject

class UpdateProfileActivity : AppCompatActivity() {
    private val argsUpdateProfile by lazy {
        intent?.getParcelableExtra(ARGS_UPDATE_PROFILE) ?: UpdateProfileArgs(
            firstName = "",
            lastName = ""
        )
    }

    private val updateProfileViewModel: UpdateProfileViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UpdateProfileScreen(updateProfileViewModel.state.collectAsState())
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun UpdateProfileScreen(state: State<UpdateProfileState>) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.profile_text_updateprofile_screen)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            finish()
                        }) {
                            Icon(Icons.Filled.ArrowBack, "backIcon")
                        }
                    },
                )
            }, content = {
                RegisterFormView(state = state)
            }
        )
    }

    @Composable
    private fun RegisterFormView(state: State<UpdateProfileState>) {
        val inputFirstName =
            remember { mutableStateOf(TextFieldValue(argsUpdateProfile.firstName)) }
        val inputLastName = remember { mutableStateOf(TextFieldValue(argsUpdateProfile.lastName)) }
        val firstNameInputError = remember { mutableStateOf(Exception()) }
        val lastNamedInputError = remember { mutableStateOf(Exception()) }

        if (state.value.updateProfileAsync is Fail) {
            val error = (state.value.updateProfileAsync as Fail<UpdateProfileDto>).error
            showToastMessage(error.message.orEmpty())
        }


        if (state.value.updateProfileAsync is Success) {
            showToastMessage("Update Profile Berhasil")
            finish()
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = inputFirstName.value,
                onValueChange = {
                    inputFirstName.value = it
                },
                placeholder = { Text(text = stringResource(R.string.common_text_hint_firstname)) },
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Text,
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
                    if (firstNameInputError.value.message.isNullOrEmpty().not())
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                }
            )
            if (firstNameInputError.value.message.isNullOrEmpty().not()) {
                Text(
                    text = firstNameInputError.value.message.orEmpty(),
                    color = Color.Red,
                    modifier = Modifier
                )
            }
            TextField(
                value = inputLastName.value,
                onValueChange = {
                    inputLastName.value = it
                },
                placeholder = { Text(text = stringResource(R.string.common_text_hint_lastname)) },
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Text,
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
                    if (lastNamedInputError.value.message.isNullOrEmpty().not())
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                }
            )
            if (lastNamedInputError.value.message.isNullOrEmpty().not()) {
                Text(
                    text = lastNamedInputError.value.message.orEmpty(),
                    color = Color.Red,
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Button(onClick = {
                firstNameInputError.value = Exception()
                lastNamedInputError.value = Exception()

                if (inputFirstName.value.text.isEmpty()) {
                    firstNameInputError.value =
                        Exception(baseContext.getString(R.string.common_text_firstname_error))
                }

                if (inputLastName.value.text.isEmpty()) {
                    lastNamedInputError.value =
                        Exception(baseContext.getString(R.string.common_text_lastname_error))
                }

                if (firstNameInputError.value.message == null
                    && lastNamedInputError.value.message == null
                )
                    updateProfileViewModel.updateProfile(
                        firstName = inputFirstName.value.text,
                        lastName = inputLastName.value.text
                    )
            }) {
                Text(text = stringResource(R.string.commont_button_update_profile))
            }
        }


    }


    companion object {
        val ARGS_UPDATE_PROFILE = "ARGS_REGISTER"
    }

    // @Preview function is use to see preview
// for our composable function in preview section
    @SuppressLint("UnrememberedMutableState")
    @Preview(name = "Login Screen", showBackground = true, showSystemUi = true)
    @Composable
    fun DefaultPreview() {
        RegisterFormView(mutableStateOf(UpdateProfileState()))
    }

}




