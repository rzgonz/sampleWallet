package com.rzgonz.nutechwallet.modules.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rzgonz.nutechwallet.R
import com.rzgonz.nutechwallet.componen.SampleComposeTheme
import com.rzgonz.nutechwallet.core.Fail
import com.rzgonz.nutechwallet.core.Loading
import com.rzgonz.nutechwallet.core.Success
import com.rzgonz.nutechwallet.core.utils.isValidEmail
import com.rzgonz.nutechwallet.modules.home.HomeActivity
import com.rzgonz.nutechwallet.modules.register.RegisterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleComposeTheme {
                LoginScreen(loginViewModel.state.collectAsState())
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
    @Composable
    private fun LoginScreen(state: State<LoginState>) {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        if (state.value.loginResponseAsync is Loading) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    baseContext.getString(R.string.common_text_loading)
                )
            }
        }

        if (state.value.loginResponseAsync is Fail) {
            val error = (state.value.loginResponseAsync as Fail<*>).error
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    error.message.orEmpty()
                )
            }
        }

        Scaffold(
            scaffoldState = scaffoldState,
            content = {
                LoginContainer(state)
            }
        )
    }

    @Composable
    fun LoginContainer(state: State<LoginState>) {

        val inputEmail = remember { mutableStateOf(TextFieldValue("dadar@gmail.com")) }
        val inputPassword = remember { mutableStateOf(TextFieldValue("123456")) }
        val passwordVisible = remember { mutableStateOf(false) }
        val emailInputError = remember { mutableStateOf(Exception()) }
        val passwordInputError = remember { mutableStateOf(Exception()) }

        if (state.value.loginResponseAsync is Success) {
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = inputEmail.value,
                onValueChange = {
                    inputEmail.value = it
                },
                placeholder = { Text(text = "Enter User Name") },
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
                }, enabled = (state.value.loginResponseAsync is Loading).not()
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
                    keyboardType = KeyboardType.Password,
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    // below line is used to add font
                    // size for our text field
                    fontSize = 15.sp,

                    // below line is use to change font family.
                    fontFamily = FontFamily.SansSerif
                ),
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible.value)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Please provide localized description for accessibility services
                    val description =
                        if (passwordVisible.value) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(imageVector = image, description)
                    }
                }, enabled = (state.value.loginResponseAsync is Loading).not()
            )
            Button(onClick = {
                emailInputError.value = Exception()
                passwordInputError.value = Exception()
                if (isValidEmail(inputEmail.value.text)) {
                    if (checkPasswordStrong(inputPassword.value.text)) {
                        loginViewModel.login(
                            email = inputEmail.value.text,
                            password = inputPassword.value.text
                        )
                    } else {
                        passwordInputError.value =
                            Exception(baseContext.getString(R.string.invalid_password))
                    }
                } else {
                    if (inputEmail.value.text.isEmpty()) {
                        emailInputError.value =
                            Exception(baseContext.getString(R.string.common_text_email_empty))
                    } else {
                        emailInputError.value =
                            Exception(baseContext.getString(R.string.common_text_email_not_valid))
                    }
                }
            }) {
                Text(text = stringResource(R.string.login_button_login))
            }
            if (passwordInputError.value.message.isNullOrEmpty().not()) {
                Text(
                    text = passwordInputError.value.message.orEmpty(),
                    color = Color.Red,
                    modifier = Modifier
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(text = stringResource(R.string.login_button_register),
                color = Color.Blue,
                modifier = Modifier.clickable {
                    startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                })
        }
    }

    private fun checkPasswordStrong(password: String): Boolean {
        return password.length > 5
    }

    // @Preview function is use to see preview
// for our composable function in preview section
    @SuppressLint("UnrememberedMutableState")
    @Preview(name = "Login Screen", showBackground = true, showSystemUi = true)
    @Composable
    fun DefaultPreview() {
        LoginContainer(mutableStateOf(LoginState()))
    }
}