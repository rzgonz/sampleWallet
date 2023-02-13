package com.rzgonz.nutechwallet.modules.home

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
import com.rzgonz.nutechwallet.data.dto.TransactionHistoryDto
import com.rzgonz.nutechwallet.data.dto.TransactionHistoryItemDto
import com.rzgonz.nutechwallet.data.dto.UserProfileDto
import com.rzgonz.nutechwallet.domain.PaymentUseCase
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

data class HomeState(
    val userProfileAsync: Async<UserProfileDto> = Uninitialized,
    val balanceAsync: Async<BalanceDto> = Uninitialized,
    val transactionHistoryAsync: Async<List<TransactionHistoryItemDto>> = Uninitialized,
)

class HomeViewModel(
    private val userUseCase: UserUseCase,
    private val paymentUseCase: PaymentUseCase,
    private val gson: Gson
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun getProfile() {
        _state.update { it.copy(userProfileAsync = Loading()) }
        viewModelScope.launch {
            try {
                val data = userUseCase.getProfileUser()
                _state.update { it.copy(userProfileAsync = Success(data)) }
            } catch (e: Exception) {
                val message = safeLaunchWithResult(e.message.orEmpty()) {
                    val response = (e as? HttpException)?.response()?.errorBody()?.string()
                    val gson = gson.fromJson(response, BaseResponse::class.java)
                    gson.message.orEmpty()
                }
                _state.update { it.copy(userProfileAsync = (Fail(Throwable(message)))) }
            }
        }
    }


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

    fun getHistoryTransaction() {
        _state.update { it.copy(transactionHistoryAsync = Loading()) }
        viewModelScope.launch {
            try {
                val data = paymentUseCase.transactionHistory()
                _state.update { it.copy(transactionHistoryAsync = Success(data.data)) }
            } catch (e: Exception) {
                val message = safeLaunchWithResult(e.message.orEmpty()) {
                    val response = (e as? HttpException)?.response()?.errorBody()?.string()
                    val gson = gson.fromJson(response, TransactionHistoryDto::class.java)
                    gson.message
                }
                _state.update { it.copy(transactionHistoryAsync = (Fail(Throwable(message)))) }
            }
        }
    }


}