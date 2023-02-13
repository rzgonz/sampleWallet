package com.rzgonz.nutechwallet.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by rzgonz on 12/02/23.
 *
 */
data class RegisterResponse (
    @field:SerializedName("last_name")
    val lastName: String? = null,

    @field:SerializedName("first_name")
    val firstName: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)