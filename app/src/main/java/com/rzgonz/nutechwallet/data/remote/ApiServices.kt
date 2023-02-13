package com.rzgonz.nutechwallet.data.remote

import com.rzgonz.nutechwallet.core.BaseResponse
import com.rzgonz.nutechwallet.data.remote.request.LoginRequest
import com.rzgonz.nutechwallet.data.remote.request.RegisterRequest
import com.rzgonz.nutechwallet.data.remote.request.TopupRequest
import com.rzgonz.nutechwallet.data.remote.request.TransferRequest
import com.rzgonz.nutechwallet.data.remote.request.UpdateProfileRequest
import com.rzgonz.nutechwallet.data.remote.response.BalanceResponse
import com.rzgonz.nutechwallet.data.remote.response.LoginReponse
import com.rzgonz.nutechwallet.data.remote.response.RegisterResponse
import com.rzgonz.nutechwallet.data.remote.response.TransactionHistoryResponse
import com.rzgonz.nutechwallet.data.remote.response.UpdateProfileResponse
import com.rzgonz.nutechwallet.data.remote.response.UserProfileResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by rzgonz on 12/02/23.
 *
 */
interface ApiServices {

    @POST("registration")
    suspend fun register(
        @Body requestRequest: RegisterRequest
    ): BaseResponse<RegisterResponse>


    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): BaseResponse<LoginReponse>


    @POST("updateProfile")
    suspend fun updateProfile(
        @Body updateProfileRequest: UpdateProfileRequest
    ): BaseResponse<UpdateProfileResponse>

    @GET("getProfile")
    suspend fun getProfile(): BaseResponse<UserProfileResponse>

    @POST("topup")
    suspend fun topup(
        @Body topupRequest: TopupRequest
    ): BaseResponse<Any>


    @POST("transfer")
    suspend fun transfer(
        @Body transferRequest: TransferRequest
    ): BaseResponse<Any>

    @GET("transactionHistory")
    suspend fun transactionHistory(): TransactionHistoryResponse

    @GET("balance")
    suspend fun balance(): BaseResponse<BalanceResponse>

}