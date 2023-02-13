package com.rzgonz.nutechwallet.core.network


import android.util.Log
import com.rzgonz.nutechwallet.core.ForceLogoutEvent
import com.rzgonz.nutechwallet.core.utils.tag
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.greenrobot.eventbus.EventBus
import org.koin.core.component.KoinComponent
import javax.net.ssl.HttpsURLConnection


class RefreshTokenAuthenticator : Authenticator, KoinComponent {

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d(tag<RefreshTokenAuthenticator>(), "Authenticate: $response")

        if (response.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
            NetworkUtils.setAuthToken("")
            sendLogOutEvent()
        }
        return null
    }

    private fun sendLogOutEvent() {
        EventBus.getDefault().post(ForceLogoutEvent())
    }

}