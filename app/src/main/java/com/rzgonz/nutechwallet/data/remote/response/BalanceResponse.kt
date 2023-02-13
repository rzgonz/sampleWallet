package com.rzgonz.nutechwallet.data.remote.response

import com.google.gson.annotations.SerializedName

data class BalanceResponse(

	@field:SerializedName("balance")
	val balance: Int? = null
)
