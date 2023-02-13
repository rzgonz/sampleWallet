package com.rzgonz.nutechwallet.domain

import com.rzgonz.nutechwallet.data.AppRepository
import com.rzgonz.nutechwallet.data.DataMapper
import com.rzgonz.nutechwallet.data.dto.BalanceDto
import com.rzgonz.nutechwallet.data.dto.TransactionHistoryDto
import com.rzgonz.nutechwallet.data.remote.request.TopupRequest
import com.rzgonz.nutechwallet.data.remote.request.TransferRequest
import com.rzgonz.nutechwallet.data.remote.response.TransactionHistoryResponse

/**
 * Created by rzgonz on 12/02/23.
 *
 */
class PaymentUseCaseImpl(
    private val repository: AppRepository
) : PaymentUseCase {
    override suspend fun getBalance(): BalanceDto {
        val response = repository.balance()
        return DataMapper.balanceResponseToDto(response.data)
    }

    override suspend fun transfer(transferRequest: TransferRequest): Any {
        val response = repository.transfer(transferRequest)
        return response.data.toString()
    }

    override suspend fun topUp(topupRequest: TopupRequest): Any {
        val response = repository.topup(topupRequest)
        return response.data.toString()
    }

    override suspend fun transactionHistory(): TransactionHistoryDto {
        val response = repository.transactionHistory()
        return DataMapper.transactionHistoryResponseToDto(response)
    }
}