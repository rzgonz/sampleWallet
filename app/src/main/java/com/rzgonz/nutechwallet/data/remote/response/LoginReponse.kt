package com.rzgonz.nutechwallet.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginReponse(

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
