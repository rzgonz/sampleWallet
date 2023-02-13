package com.rzgonz.nutechwallet.core.network


object NetworkUtils {

    const val SUCCESS_RESPONSE_CODE = 0

    private var baseUrl = ""
    private var authToken = ""

    fun setBaseUrl(baseUrl: String) {
        this.baseUrl = baseUrl
    }

    fun getBaseUrl(): String = baseUrl

    fun setAuthToken(authToken: String) {
        this.authToken = authToken
    }

    fun getAuthToken(): String = authToken
}
