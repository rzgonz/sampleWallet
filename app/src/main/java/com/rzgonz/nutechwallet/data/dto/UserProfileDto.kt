package com.rzgonz.nutechwallet.data.dto

import com.google.gson.annotations.SerializedName

data class UserProfileDto(
	val lastName: String = "",
	val firstName: String = "",
	val email: String = ""
)
