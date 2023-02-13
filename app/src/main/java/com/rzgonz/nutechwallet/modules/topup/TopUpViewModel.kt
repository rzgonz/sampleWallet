package com.rzgonz.nutechwallet.modules.topup

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
import com.rzgonz.nutechwallet.data.remote.request.TopupRequest
import com.rzgonz.nutechwallet.domain.PaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

/**
 * Created by rzgonz on 13/02/23.
 *
 */

data class TopUpState(
    val topUpAsync: Async<Any> = Uninitialized
)

class TopUpViewModel(
    private val paymentUseCase: PaymentUseCase,
    private val gson: Gson
) : ViewModel() {

    private val _state = MutableStateFlow(TopUpState())
    val state = _state.asStateFlow()

    fun topUp(amount: Int) {
        _state.update { it.copy(topUpAsync = Loading()) }
        viewModelScope.launch {
            try {
                val data = paymentUseCase.topUp(TopupRequest(amount = amount))
                _state.update { it.copy(topUpAsync = Success(data)) }
            } catch (e: Exception) {
                val message = safeLaunchWithResult(e.message.orEmpty()) {
                    val response = (e as? HttpException)?.response()?.errorBody()?.string()
                    val gson = gson.fromJson(response, BaseResponse::class.java)
                    gson.message.orEmpty()
                }
                _state.update { it.copy(topUpAsync = (Fail(Throwable(message)))) }
            }
        }
    }


}