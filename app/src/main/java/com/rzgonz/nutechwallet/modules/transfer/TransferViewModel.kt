package com.rzgonz.nutechwallet.modules.transfer

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
import com.rzgonz.nutechwallet.data.dto.BalanceDto
import com.rzgonz.nutechwallet.data.remote.request.TransferRequest
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

data class TransferState(
    val transferAsync: Async<Any> = Uninitialized,
    val balanceAsync: Async<BalanceDto> = Uninitialized
)

class TransferViewModel(
    private val paymentUseCase: PaymentUseCase,
    private val gson: Gson
) : ViewModel() {

    private val _state = MutableStateFlow(TransferState())
    val state = _state.asStateFlow()

    fun getBalance() {
        _state.update { it.copy(balanceAsync = Loading()) }
        viewModelScope.launch {
            try {
                val data = paymentUseCase.getBalance()
                _state.update { it.copy(balanceAsync = Success(data)) }
            } catch (e: Exception) {
                val message = safeLaunchWithResult(e.message.orEmpty()) {
                    val response = (e as? HttpException)?.response()?.errorBody()?.string()
                    val gson = gson.fromJson(response, BaseResponse::class.java)
                    gson.message.orEmpty()
                }
                _state.update { it.copy(balanceAsync = (Fail(Throwable(message)))) }
            }
        }
    }

    fun transfer(amount: Int) {
        _state.update { it.copy(transferAsync = Loading()) }
        viewModelScope.launch {
            try {
                val data = paymentUseCase.transfer(TransferRequest(amount = amount))
                _state.update { it.copy(transferAsync = Success(data)) }
            } catch (e: Exception) {
                val message = safeLaunchWithResult(e.message.orEmpty()) {
                    val response = (e as? HttpException)?.response()?.errorBody()?.string()
                    val gson = gson.fromJson(response, BaseResponse::class.java)
                    gson.message.orEmpty()
                }
                _state.update { it.copy(transferAsync = (Fail(Throwable(message)))) }
            }
        }
    }
}