package com.rzgonz.nutechwallet.core

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(

    @field:SerializedName("data")
    val data: T? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

