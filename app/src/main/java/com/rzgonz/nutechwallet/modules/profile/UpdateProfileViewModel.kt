package com.rzgonz.nutechwallet.modules.profile

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
import com.rzgonz.nutechwallet.data.dto.UpdateProfileDto
import com.rzgonz.nutechwallet.data.remote.request.UpdateProfileRequest
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

data class UpdateProfileState(
    val updateProfileAsync: Async<UpdateProfileDto> = Uninitialized
)

class UpdateProfileViewModel(
    private val userUseCase: UserUseCase,
    private val gson: Gson
) : ViewModel() {

    private val _state = MutableStateFlow(UpdateProfileState())
    val state = _state.asStateFlow()

    fun updateProfile(firstName: String, lastName: String) {
        _state.update { it.copy(updateProfileAsync = Loading()) }
        viewModelScope.launch {
            try {
                val data = userUseCase.updateProfile(
                    UpdateProfileRequest(
                        firstName = firstName,
                        lastName = lastName
                    )
                )
                _state.update { it.copy(updateProfileAsync = Success(data)) }
            } catch (e: Exception) {
                val message = safeLaunchWithResult(e.message.orEmpty()) {
                    val response = (e as? HttpException)?.response()?.errorBody()?.string()
                    val gson = gson.fromJson(response, BaseResponse::class.java)
                    gson.message.orEmpty()
                }
                _state.update { it.copy(updateProfileAsync = (Fail(Throwable(message)))) }
            }
        }
    }


}