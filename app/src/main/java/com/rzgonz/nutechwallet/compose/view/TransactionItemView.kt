package com.rzgonz.nutechwallet.compose.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rzgonz.nutechwallet.R
import com.rzgonz.nutechwallet.componen.SampleComposeTheme
import com.rzgonz.nutechwallet.core.TransactionType
import com.rzgonz.nutechwallet.core.utils.TimeUtils
import com.rzgonz.nutechwallet.core.utils.decimalFormat
import com.rzgonz.nutechwallet.data.dto.TransactionHistoryItemDto

@Composable
fun TransactionItemView(
    data: TransactionHistoryItemDto,
) {
    Card(
        modifier = Modifier
            .border(1.dp, Color.Black)
            .background(Color.DarkGray)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(
                    1f
                )
            ) {
                Text(
                    text =
                    stringResource(
                        id = R.string.transaction_text_transactionId, data.transactionId
                    ), color =
                    Color.Black, fontSize = 18.sp
                )
                Text(
                    text = stringResource(
                        id = R.string.transaction_text_date, TimeUtils.formatStringDate(
                            data.transactionTime, TimeUtils.DATE_FROM_BE, TimeUtils.DATE_IN_ID
                        )
                    ),
                    color = Color.Gray, fontSize = 12.sp
                )
                Text(
                    text = data.transactionType + " Rp. " + data.amount.decimalFormat(),
                    fontSize = 18.sp,
                    color = if (data.transactionType == TransactionType.TRANSFER.transactionType) Color.Red else Color.Blue
                )
            }
            Image(
                painter = painterResource(
                    id =
                    if (data.transactionType == TransactionType.TRANSFER.transactionType) R.drawable.transaction_ic_out else R.drawable.transaction_ic_in
                ),
                contentDescription = "",
                modifier = Modifier.size(32.dp, 32.dp)
            )
        }
    }

}

@Preview(name = "list item transaction list topup", showBackground = true)
@Composable
private fun topUpPreview() {
    SampleComposeTheme {
        TransactionItemView(
            data =
            TransactionHistoryItemDto(
                transactionId = "1",
                transactionTime = "2022-07-19",
                transactionType = TransactionType.TOPUP.transactionType,
                amount = 200
            )
        )
    }
}

@Preview(name = "list item transaction list transfer", showBackground = true)
@Composable
private fun transferPreview() {
    SampleComposeTheme {
        TransactionItemView(
            data =
            TransactionHistoryItemDto(
                transactionId = "2",
                transactionTime = "2022-07-19",
                transactionType = TransactionType.TRANSFER.transactionType,
                amount = 2000000
            )
        )
    }
}