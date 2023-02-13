package com.rzgonz.nutechwallet.modules.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rzgonz.nutechwallet.core.Async
import com.rzgonz.nutechwallet.core.BaseResponse
import com.rzgonz.nutechwallet.core.Fail
import com.rzgonz.nutechwallet.core.Loading
import com.rzgonz.nutechwallet.core.Success
import com.rzgonz.nutechwallet.core.Uninitialized
import com.rzgonz.nutechwallet.core.utils.safeLaunchWithResult
import com.rzgonz.nutechwallet.data.dto.RegisterDto
import com.rzgonz.nutechwallet.data.remote.request.RegisterRequest
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

data class RegisterState(
    val registerResponseAsync: Async<RegisterDto> = Uninitialized
)

class RegisterViewModel(
    private val userUseCase: UserUseCase,
    private val gson: Gson
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun register(email: String, password: String, firstName: String, lastName: String) {
        _state.update { it.copy(registerResponseAsync = Loading()) }
        viewModelScope.launch {
            try {
                val data = userUseCase.register(
                    RegisterRequest(
                        password = password, email = email,
                        firstName = firstName, lastName = lastName
                    )
                )
                _state.update { it.copy(registerResponseAsync = Success(data)) }
            } catch (e: Exception) {
                val message = safeLaunchWithResult(e.message.orEmpty()) {
                    val response = (e as? HttpException)?.response()?.errorBody()?.string()
                    val gson = gson.fromJson(response, BaseResponse::class.java)
                    gson.message.orEmpty()
                }
                _state.update { it.copy(registerResponseAsync = (Fail(Throwable(message)))) }
            }
        }
    }


}