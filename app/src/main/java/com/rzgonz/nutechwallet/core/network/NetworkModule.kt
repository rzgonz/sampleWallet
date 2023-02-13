package com.rzgonz.nutechwallet.core.network

import com.rzgonz.nutechwallet.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NetworkModule : KoinComponent {

    private val requestAuthInterceptor: RequestAuthInterceptor by inject()
    private val refreshTokenAuthenticator: RefreshTokenAuthenticator by inject()

    private var requestTimeOut = 120L

    private val builder by lazy {
        OkHttpClient.Builder()
    }

    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(NetworkUtils.getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    fun getClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

        val httpClient = builder.apply {
            addNetworkInterceptor(logInterceptor)
            addNetworkInterceptor(requestAuthInterceptor)
            authenticator(refreshTokenAuthenticator)

            callTimeout(requestTimeOut, TimeUnit.SECONDS)
            connectTimeout(requestTimeOut, TimeUnit.SECONDS)
            readTimeout(requestTimeOut, TimeUnit.SECONDS)
            writeTimeout(requestTimeOut, TimeUnit.SECONDS)
        }

        return httpClient.build()
    }

}