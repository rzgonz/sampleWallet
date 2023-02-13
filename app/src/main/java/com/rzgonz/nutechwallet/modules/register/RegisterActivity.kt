package com.rzgonz.nutechwallet.modules.register

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
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.rzgonz.nutechwallet.core.Loading
import com.rzgonz.nutechwallet.core.Success
import com.rzgonz.nutechwallet.core.utils.isValidEmail
import com.rzgonz.nutechwallet.core.utils.showToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {
    private val extraEmail by lazy {
        intent?.getStringExtra(ARGS_REGISTER).orEmpty()
    }

    private val registerViewModel: RegisterViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterScreen(registerViewModel.state.collectAsState())
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
    @Composable
    private fun RegisterScreen(state: State<RegisterState>) {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        if (state.value.registerResponseAsync is Loading) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    baseContext.getString(R.string.common_text_loading)
                )
            }
        }

        if (state.value.registerResponseAsync is Fail) {
            val error = (state.value.registerResponseAsync as Fail<*>).error
            showToastMessage(error.message.orEmpty())
        }
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.register_text_register_screen)) },
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
    private fun RegisterFormView(state: State<RegisterState>) {
        val inputEmail = remember { mutableStateOf(TextFieldValue(extraEmail)) }
        val inputPassword = remember { mutableStateOf(TextFieldValue()) }
        val inputFirstName = remember { mutableStateOf(TextFieldValue()) }
        val inputLastName = remember { mutableStateOf(TextFieldValue()) }
        val emailInputError = remember { mutableStateOf(Exception()) }
        val passwordInputError = remember { mutableStateOf(Exception()) }
        val firstNameInputError = remember { mutableStateOf(Exception()) }
        val lastNamedInputError = remember { mutableStateOf(Exception()) }

        if (state.value.registerResponseAsync is Success) {
            showToastMessage(stringResource(R.string.register_text_succes_register))
            finish()
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            TextField(
                value = inputEmail.value,
                onValueChange = {
                    inputEmail.value = it
                },
                placeholder = { Text(text = "Enter Email") },
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
                    if (emailInputError.value.message.isNullOrEmpty().not())
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                },
                enabled = (state.value.registerResponseAsync is Loading).not()
            )
            if (emailInputError.value.message.isNullOrEmpty().not()) {
                Text(
                    text = emailInputError.value.message.orEmpty(),
                    color = Color.Red,
                    modifier = Modifier
                )
            }
            TextField(
                value = inputPassword.value,
                onValueChange = {
                    inputPassword.value = it
                },
                placeholder = { Text(text = "Enter Password") },
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
                enabled = (state.value.registerResponseAsync is Loading).not()
            )
            if (passwordInputError.value.message.isNullOrEmpty().not()) {
                Text(
                    text = passwordInputError.value.message.orEmpty(),
                    color = Color.Red,
                    modifier = Modifier
                )
            }


            TextField(
                value = inputFirstName.value,
                onValueChange = {
                    inputFirstName.value = it
                },
                placeholder = { Text(text = stringResource(id = R.string.common_text_hint_firstname)) },
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
                },
                enabled = (state.value.registerResponseAsync is Loading).not()
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
                placeholder = { Text(text = stringResource(id = R.string.common_text_hint_lastname)) },
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
                },
                enabled = (state.value.registerResponseAsync is Loading).not()
            )
            if (lastNamedInputError.value.message.isNullOrEmpty().not()) {
                Text(
                    text = lastNamedInputError.value.message.orEmpty(),
                    color = Color.Red,
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Button(
                onClick = {
                    emailInputError.value = Exception()
                    passwordInputError.value = Exception()
                    firstNameInputError.value = Exception()
                    lastNamedInputError.value = Exception()


                    if (isValidEmail(inputEmail.value.text).not()) {
                        if (inputEmail.value.text.isEmpty()) {
                            emailInputError.value =
                                Exception(baseContext.getString(R.string.common_text_email_empty))
                        } else {
                            emailInputError.value =
                                Exception(baseContext.getString(R.string.common_text_email_not_valid))
                        }
                    }

                    if (inputPassword.value.text.length < 6) {
                        passwordInputError.value = Exception("Password min 6 character")
                    }

                    if (inputFirstName.value.text.isEmpty()) {
                        firstNameInputError.value =
                            Exception(baseContext.getString(R.string.common_text_firstname_error))
                    }

                    if (inputLastName.value.text.isEmpty()) {
                        lastNamedInputError.value =
                            Exception(baseContext.getString(R.string.common_text_lastname_error))
                    }

                    if (emailInputError.value.message == null
                        && passwordInputError.value.message == null
                        && firstNameInputError.value.message == null
                        && lastNamedInputError.value.message == null
                    )
                        registerViewModel.register(
                            email = inputEmail.value.text,
                            password = inputPassword.value.text,
                            firstName = inputFirstName.value.text,
                            lastName = inputLastName.value.text
                        )
                },
                enabled = (state.value.registerResponseAsync is Loading).not()
            ) {
                Text(text = "Register")
            }
        }


    }


    companion object {
        private val ARGS_REGISTER = "ARGS_REGISTER"
    }

    // @Preview function is use to see preview
// for our composable function in preview section
    @SuppressLint("UnrememberedMutableState")
    @Preview(name = "Login Screen", showBackground = true, showSystemUi = true)
    @Composable
    fun DefaultPreview() {
        RegisterFormView(mutableStateOf(RegisterState()))
    }

}




