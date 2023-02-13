package com.rzgonz.nutechwallet

import android.app.Application
import android.content.Intent
import com.rzgonz.nutechwallet.core.ForceLogoutEvent
import com.rzgonz.nutechwallet.core.network.NetworkUtils
import com.rzgonz.nutechwallet.core.provider.AppModulesProvider
import com.rzgonz.nutechwallet.modules.login.LoginActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module


open class Applications : Application() {

    override fun onCreate() {
        super.onCreate()
        NetworkUtils.setBaseUrl(
            "https://tht-api.nutech-integrasi.app/"
        )
        setupKoin()
        initForceLogout()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Applications)

            modules(mutableListOf<Module>().apply {
                addAll(
                    AppModulesProvider.getInstance().appModules
                )
            })
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(event: ForceLogoutEvent) {
        // Do something
        applicationContext.startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }


    private fun initForceLogout() {
        EventBus.getDefault().register(this)
    }

    override fun onTerminate() {
        EventBus.getDefault().unregister(this)
        super.onTerminate()

    }


}