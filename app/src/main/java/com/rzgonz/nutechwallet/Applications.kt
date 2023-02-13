package com.rzgonz.nutechwallet

import android.app.Application
import com.rzgonz.nutechwallet.core.network.NetworkUtils
import com.rzgonz.nutechwallet.core.provider.AppModulesProvider
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


}