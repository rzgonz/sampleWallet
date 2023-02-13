package com.rzgonz.nutechwallet.data

import com.rzgonz.nutechwallet.core.utils.orZero
import com.rzgonz.nutechwallet.data.dto.BalanceDto
import com.rzgonz.nutechwallet.data.dto.LoginDto
import com.rzgonz.nutechwallet.data.dto.RegisterDto
import com.rzgonz.nutechwallet.data.dto.TransactionHistoryDto
import com.rzgonz.nutechwallet.data.dto.TransactionHistoryItemDto
import com.rzgonz.nutechwallet.data.dto.UpdateProfileDto
import com.rzgonz.nutechwallet.data.dto.UserProfileDto
import com.rzgonz.nutechwallet.data.remote.response.BalanceResponse
import com.rzgonz.nutechwallet.data.remote.response.LoginReponse
import com.rzgonz.nutechwallet.data.remote.response.RegisterResponse
import com.rzgonz.nutechwallet.data.remote.response.TransactionHistoryItemResponse
import com.rzgonz.nutechwallet.data.remote.response.TransactionHistoryResponse
import com.rzgonz.nutechwallet.data.remote.response.UpdateProfileResponse
import com.rzgonz.nutechwallet.data.remote.response.UserProfileResponse

/**
 * Created by rzgonz on 12/02/23.
 *
 */
object DataMapper {

    fun balanceResponseToDto(response: BalanceResponse?): BalanceDto {
        return BalanceDto(balance = response?.balance.orZero())
    }

    fun loginResponseToDto(response: LoginReponse?): LoginDto {
        return LoginDto(
            lastName = response?.lastName.orEmpty(),
            firstName = response?.firstName.orEmpty(),
            email = response?.email.orEmpty(),
            token = response?.token.orEmpty()
        )
    }

    fun registerResponseToDto(response: RegisterResponse?): RegisterDto {
        return RegisterDto(
            lastName = response?.lastName.orEmpty(),
            firstName = response?.firstName.orEmpty(),
            email = response?.email.orEmpty()
        )
    }

    fun transactionHistoryItemResponseToDto(
        response: TransactionHistoryItemResponse?
    ): TransactionHistoryItemDto {
        return TransactionHistoryItemDto(
            transactionId = response?.transactionId.orEmpty(),
            amount = response?.amount.orZero(),
            transactionTime = response?.transactionTime.orEmpty(),
            transactionType = response?.transactionType.orEmpty()
        )
    }

    fun transactionHistoryResponseToDto(
        response: TransactionHistoryResponse?
    ): TransactionHistoryDto {
        return TransactionHistoryDto(
            message = response?.message.orEmpty(),
            data = response?.data?.map {
                transactionHistoryItemResponseToDto(it)
            }.orEmpty()
        )
    }

    fun updateProfileResponseToDto(
        response: UpdateProfileResponse?
    ): UpdateProfileDto {
        return UpdateProfileDto(
            lastName = response?.lastName.orEmpty(),
            firstName = response?.firstName.orEmpty()
        )
    }

    fun userProfileResponseToDto(
        response: UserProfileResponse?
    ): UserProfileDto {
        return UserProfileDto(
            lastName = response?.lastName.orEmpty(),
            firstName = response?.firstName.orEmpty(),
            email = response?.email.orEmpty()
        )
    }

}