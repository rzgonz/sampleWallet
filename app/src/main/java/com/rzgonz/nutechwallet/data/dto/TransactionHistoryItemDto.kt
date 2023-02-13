package com.rzgonz.nutechwallet.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by rzgonz on 12/02/23.
 *
 */
class TransactionHistoryItemDto(
    @field:SerializedName("transaction_id")
    val transactionId: String = "",

    @field:SerializedName("amount")
    val amount: Int = 0,

    @field:SerializedName("transaction_time")
    val transactionTime: String = "",

    @field:SerializedName("transaction_type")
    val transactionType: String = ""
)