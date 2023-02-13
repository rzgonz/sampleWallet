package com.rzgonz.nutechwallet.core.provider

import com.google.gson.Gson
import com.rzgonz.nutechwallet.core.utils.clazz
import com.rzgonz.nutechwallet.core.network.NetworkModule
import com.rzgonz.nutechwallet.core.network.RequestAuthInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module

class NetworkProvider private constructor() : BaseModuleProvider {

    override val modules: List<Module>
        get() = listOf(networkModule)

    private val networkModule = module {
        single { Gson() }
        single { RequestAuthInterceptor() }
        single { NetworkModule.getClient() }
        single { NetworkModule.getRetrofit(okHttpClient = get()) }
    }


    companion object {

        @Volatile
        private var INSTANCE: NetworkProvider? = null

        @JvmStatic
        fun getInstance(): NetworkProvider {
            return INSTANCE ?: synchronized(clazz<NetworkProvider>()) {
                return@synchronized NetworkProvider()
            }.also {
                INSTANCE = it
            }
        }

    }
}