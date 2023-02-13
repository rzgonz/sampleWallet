package com.rzgonz.nutechwallet.data.remote.request

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(

	@field:SerializedName("last_name")
	val lastName: String,

	@field:SerializedName("first_name")
	val firstName: String
)
