package com.rzgonz.nutechwallet.compose.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rzgonz.nutechwallet.componen.SampleComposeTheme
import com.rzgonz.nutechwallet.core.Async
import com.rzgonz.nutechwallet.core.Fail
import com.rzgonz.nutechwallet.core.Loading
import com.rzgonz.nutechwallet.core.Success
import com.rzgonz.nutechwallet.core.TransactionType
import com.rzgonz.nutechwallet.data.dto.TransactionHistoryItemDto

@Composable
fun ListTransactionView(
    dataTransaction: Async<List<TransactionHistoryItemDto>>,
) {
    when (dataTransaction) {
        is Loading -> {
            LoadingView()
        }
        is Success -> {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(dataTransaction.invoke()) { data ->
                    TransactionItemView(data = data)
                }
            }
        }
        is Fail -> ErrorView(message = dataTransaction.error.message.orEmpty())
        else -> {

        }
    }

}

@Composable
private fun ErrorView(message: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = message, fontSize = 18.sp, color = Color.Red)
    }
}


@Composable
private fun LoadingView() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
}


@Preview(name = "transaction History preview", showBackground = true)
@Composable
private fun listHistoryTrancation() {
    SampleComposeTheme {
        ListTransactionView(
            dataTransaction = Success(
                listOf(
                    TransactionHistoryItemDto(
                        transactionId = "2",
                        transactionTime = "2022-07-19",
                        transactionType = TransactionType.TRANSFER.transactionType,
                        amount = 200
                    ),
                    TransactionHistoryItemDto(
                        transactionId = "1",
                        transactionTime = "2022-07-19",
                        transactionType = TransactionType.TOPUP.transactionType,
                        amount = 200
                    ),
                    TransactionHistoryItemDto(
                        transactionId = "2",
                        transactionTime = "2022-07-19",
                        transactionType = TransactionType.TRANSFER.transactionType,
                        amount = 200
                    ),
                    TransactionHistoryItemDto(
                        transactionId = "1",
                        transactionTime = "2022-07-19",
                        transactionType = TransactionType.TOPUP.transactionType,
                        amount = 200
                    ), TransactionHistoryItemDto(
                        transactionId = "2",
                        transactionTime = "2022-07-19",
                        transactionType = TransactionType.TRANSFER.transactionType,
                        amount = 200
                    ),
                    TransactionHistoryItemDto(
                        transactionId = "1",
                        transactionTime = "2022-07-19",
                        transactionType = TransactionType.TOPUP.transactionType,
                        amount = 200
                    )
                )
            )
        )
    }
}


@Preview(name = "transaction History loading preview", showBackground = true)
@Composable
private fun listHistoryTrancationLoading() {
    SampleComposeTheme {
        ListTransactionView(
            dataTransaction = Loading()
        )
    }
}


@Preview(name = "transaction History fail preview", showBackground = true)
@Composable
private fun listHistoryTrancatiFail() {
    SampleComposeTheme {
        ListTransactionView(
            dataTransaction = Fail(Throwable("Error message"))
        )
    }
}


