package com.rzgonz.nutechwallet.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by rzgonz on 12/02/23.
 *
 */
data class TransactionHistoryItemResponse(

    @field:SerializedName("transaction_id")
    val transactionId: String? = null,

    @field:SerializedName("amount")
    val amount: Int? = null,

    @field:SerializedName("transaction_time")
    val transactionTime: String? = null,

    @field:SerializedName("transaction_type")
    val transactionType: String? = null
)