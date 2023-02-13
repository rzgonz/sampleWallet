package com.rzgonz.nutechwallet.compose.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
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
import com.rzgonz.nutechwallet.core.Async
import com.rzgonz.nutechwallet.core.Loading
import com.rzgonz.nutechwallet.core.Success
import com.rzgonz.nutechwallet.core.utils.decimalFormat
import com.rzgonz.nutechwallet.core.utils.orZero
import com.rzgonz.nutechwallet.data.dto.BalanceDto
import com.rzgonz.nutechwallet.data.dto.UserProfileDto

@Composable
fun HomeCardItemView(
    dataUser: Async<UserProfileDto>,
    dataBalance: Async<BalanceDto>,
    goToTopUp: () -> Unit,
    goToTransfer: () -> Unit,
    goToUpdateProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .clickable {
                    goToTransfer.invoke()
                },
        ) {
            Text(
                text =
                if (dataUser is Loading) "Loading" else
                    stringResource(
                        id = R.string.home_text_hallo,
                        dataUser.invoke()?.firstName.orEmpty()
                    ), color =
                Color.Black, fontSize = 21.sp,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .weight(1f)
            )
            if (dataUser is Success) {
                Image(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "",
                    modifier = Modifier
                        .size(32.dp, 32.dp)
                        .clickable {
                            goToUpdateProfile.invoke()
                        }
                )
            }
        }
        Text(
            text =
            stringResource(
                id = R.string.home_text_saldo
            ), color =
            Color.Black, fontSize = 18.sp
        )
        Column {
            Text(
                text =
                if (dataBalance is Loading) "Loading" else
                    stringResource(
                        id = R.string.home_text_balance,
                        dataBalance.invoke()?.balance.orZero().decimalFormat()
                    ), color =
                Color.Black, fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .clickable {
                        goToTopUp.invoke()
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(
                        id =
                        R.drawable.transaction_ic_in,
                    ),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp, 32.dp),
                )
                Text(
                    text =
                    stringResource(
                        id = R.string.home_text_topup
                    ), color =
                    Color.Black, fontSize = 18.sp
                )
            }
            Column(
                modifier = Modifier
                    .clickable {
                        goToTransfer.invoke()
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(
                        id =
                        R.drawable.transaction_ic_out,
                    ),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp, 32.dp)
                )
                Text(
                    text =
                    stringResource(
                        id = R.string.home_text_transfer
                    ), color =
                    Color.Black, fontSize = 18.sp
                )
            }
        }
    }
}

@Preview(name = "home card preview", showBackground = true)
@Composable
private fun homeCardPreview() {
    SampleComposeTheme {
        HomeCardItemView(
            dataUser =
            Success(
                UserProfileDto(
                    lastName = "LAST NAME",
                    firstName = "FIRST NAME",
                    email = "ajajan@gmail.com"
                )
            ),
            Success(
                BalanceDto(balance = 1000)
            ), goToTopUp = {

            },
            goToUpdateProfile = {

            },
            goToTransfer = {

            }
        )
    }
}


@Preview(name = "home card loading preview", showBackground = true)
@Composable
private fun homeCardLoadingPreview() {
    SampleComposeTheme {
        HomeCardItemView(
            dataUser = Loading(),
            Loading(
            ), goToTopUp = {

            },
            goToUpdateProfile = {

            }, goToTransfer = {

            }
        )
    }
}
