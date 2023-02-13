package com.rzgonz.nutechwallet.data.dto

data class TransactionHistoryDto(
    val message: String = "",
    val data: List<TransactionHistoryItemDto> = listOf()
)