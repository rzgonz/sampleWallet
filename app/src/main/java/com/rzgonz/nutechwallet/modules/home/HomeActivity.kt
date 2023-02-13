package com.rzgonz.nutechwallet.modules.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.rzgonz.nutechwallet.R
import com.rzgonz.nutechwallet.compose.view.HomeCardItemView
import com.rzgonz.nutechwallet.compose.view.ListTransactionView
import com.rzgonz.nutechwallet.modules.profile.UpdateProfileActivity
import com.rzgonz.nutechwallet.modules.profile.UpdateProfileArgs
import com.rzgonz.nutechwallet.modules.topup.TopUpActivity
import com.rzgonz.nutechwallet.modules.transfer.TransferActivity
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(homeViewModel.state.collectAsState())
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getProfile()
        homeViewModel.getBalance()
        homeViewModel.getHistoryTransaction()
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun HomeScreen(state: State<HomeState>) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.home_text_screen_title)) },
                )
            },
            content = {
                HomeContainer(state)
            }
        )
    }

    @Composable
    private fun HomeContainer(state: State<HomeState>) {
        Column {
            HomeCardItemView(
                dataUser = state.value.userProfileAsync,
                dataBalance = state.value.balanceAsync,
                goToTransfer = {
                    startActivity(Intent(this@HomeActivity, TransferActivity::class.java))
                },
                goToTopUp = {
                    startActivity(Intent(this@HomeActivity, TopUpActivity::class.java))
                },
                goToUpdateProfile = {
                    startActivity(
                        Intent(
                            this@HomeActivity,
                            UpdateProfileActivity::class.java
                        ).apply {
                            putExtra(
                                UpdateProfileActivity.ARGS_UPDATE_PROFILE, UpdateProfileArgs(
                                    firstName = state.value.userProfileAsync.invoke()?.firstName.orEmpty(),
                                    lastName = state.value.userProfileAsync.invoke()?.lastName.orEmpty()
                                )
                            )
                        })
                }
            )
            Text(
                text = stringResource(R.string.home_text_history_transaction), color =
                Color.Black, fontSize = 18.sp
            )
            ListTransactionView(
                dataTransaction = state.value.transactionHistoryAsync
            )
        }
    }
}
