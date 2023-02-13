package com.rzgonz.nutechwallet.data.remote.request

import com.google.gson.annotations.SerializedName

data class TopupRequest(

	@field:SerializedName("amount")
	val amount: Int
)
