package com.rzgonz.nutechwallet.modules.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rzgonz.nutechwallet.core.Async
import com.rzgonz.nutechwallet.core.BaseResponse
import com.rzgonz.nutechwallet.core.Fail
import com.rzgonz.nutechwallet.core.Loading
import com.rzgonz.nutechwallet.core.Success
import com.rzgonz.nutechwallet.core.Uninitialized
import com.rzgonz.nutechwallet.core.network.NetworkUtils
import com.rzgonz.nutechwallet.core.utils.safeLaunchWithResult
import com.rzgonz.nutechwallet.data.dto.LoginDto
import com.rzgonz.nutechwallet.data.remote.request.LoginRequest
import com.rzgonz.nutechwallet.domain.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

/**
 * Created by rzgonz on 13/02/23.
 *
 */

data class LoginState(
    val loginResponseAsync: Async<LoginDto> = Uninitialized
)

class LoginViewModel(
    private val userUseCase: UserUseCase,
    private val gson: Gson
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun login(email: String, password: String) {
        _state.update { it.copy(loginResponseAsync = Loading()) }
        viewModelScope.launch {
            try {
                val data = userUseCase.login(LoginRequest(password = password, email = email))
                    NetworkUtils.setAuthToken(data.token)
                _state.update { it.copy(loginResponseAsync = Success(data)) }
            } catch (e: Exception) {
                val message = safeLaunchWithResult(e.message.orEmpty()) {
                    val response = (e as? HttpException)?.response()?.errorBody()?.string()
                    val gson = gson.fromJson(response, BaseResponse::class.java)
                    gson.message.orEmpty()
                }
                _state.update { it.copy(loginResponseAsync = (Fail(Throwable(message)))) }
            }
        }
    }


}