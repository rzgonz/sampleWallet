package com.rzgonz.nutechwallet.core.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestAuthInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (request.header("No-Authentication") == null) {
            //val token = getTokenFromSharedPreference();
            //or use Token Function
            if (NetworkUtils.getAuthToken().isNotEmpty()) {
                val finalToken = "Bearer ${NetworkUtils.getAuthToken()}"
                request = request.newBuilder()
                    .addHeader("Authorization", finalToken)
                    .build()
            }

        }

        return chain.proceed(request)
    }


}