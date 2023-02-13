package com.rzgonz.nutechwallet.domain

import com.rzgonz.nutechwallet.data.dto.BalanceDto
import com.rzgonz.nutechwallet.data.dto.TransactionHistoryDto
import com.rzgonz.nutechwallet.data.dto.TransactionHistoryItemDto
import com.rzgonz.nutechwallet.data.remote.request.TopupRequest
import com.rzgonz.nutechwallet.data.remote.request.TransferRequest
import com.rzgonz.nutechwallet.data.remote.response.TransactionHistoryItemResponse

/**
 * Created by rzgonz on 12/02/23.
 *
 */
interface PaymentUseCase {
    suspend fun getBalance(): BalanceDto
    suspend fun transfer(transferRequest: TransferRequest): Any
    suspend fun topUp(topupRequest: TopupRequest): Any
    suspend fun transactionHistory(): TransactionHistoryDto
}