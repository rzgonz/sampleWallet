package com.rzgonz.nutechwallet.data.remote.response

import com.google.gson.annotations.SerializedName

data class TransactionHistoryResponse(
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("data")
    val data: List<TransactionHistoryItemResponse>? = null
)
