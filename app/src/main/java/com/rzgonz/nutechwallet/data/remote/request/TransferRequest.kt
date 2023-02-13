package com.rzgonz.nutechwallet.data.remote.request

import com.google.gson.annotations.SerializedName

data class TransferRequest(

    @field:SerializedName("amount")
    val amount: Int
)
