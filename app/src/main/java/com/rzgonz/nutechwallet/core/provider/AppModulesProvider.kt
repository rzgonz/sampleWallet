package com.rzgonz.nutechwallet.core.provider


import com.rzgonz.nutechwallet.core.utils.clazz
import com.rzgonz.nutechwallet.data.AppRepository
import com.rzgonz.nutechwallet.data.remote.ApiServices
import com.rzgonz.nutechwallet.data.remote.RemoteDataSource
import com.rzgonz.nutechwallet.domain.PaymentUseCase
import com.rzgonz.nutechwallet.domain.PaymentUseCaseImpl
import com.rzgonz.nutechwallet.domain.UserUseCase
import com.rzgonz.nutechwallet.domain.UserUseCaseImpl
import com.rzgonz.nutechwallet.modules.home.HomeViewModel
import com.rzgonz.nutechwallet.modules.login.LoginViewModel
import com.rzgonz.nutechwallet.modules.profile.UpdateProfileViewModel
import com.rzgonz.nutechwallet.modules.register.RegisterViewModel
import com.rzgonz.nutechwallet.modules.topup.TopUpViewModel
import com.rzgonz.nutechwallet.modules.transfer.TransferViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.binds
import org.koin.dsl.module
import retrofit2.Retrofit

class AppModulesProvider {

    val appModules: List<Module>
        get() {
            return ArrayList<Module>().apply {
                addAll(networkModules)
                addAll(listOf(appModule))
                addAll(listOf(interactorModule))
                addAll(listOf(viewModelModule))
            }
        }


    private val appModule = module {
        single {
            provideWebService(retrofit = get())
        }

        single {
            RemoteDataSource(apiServices = get())
        }


        single {
            AppRepository(remoteDataSource = get())
        }

    }

    private val interactorModule = module {
        factory {
            UserUseCaseImpl(
                repository = get()
            )
        } binds arrayOf(UserUseCase::class)
        factory {
            PaymentUseCaseImpl(
                repository = get()
            )
        } binds arrayOf(PaymentUseCase::class)
    }

    private val viewModelModule = module {
        viewModel { LoginViewModel(userUseCase = get(), gson = get()) }
        viewModel { RegisterViewModel(userUseCase = get(), gson = get()) }
        viewModel { HomeViewModel(userUseCase = get(), paymentUseCase = get(), gson = get()) }
        viewModel { TransferViewModel(paymentUseCase = get(), gson = get()) }
        viewModel { TopUpViewModel(paymentUseCase = get(), gson = get()) }
        viewModel { UpdateProfileViewModel(userUseCase = get(), gson = get()) }
    }


    private val networkModules by lazy {
        NetworkProvider.getInstance().modules
    }


    private fun provideWebService(retrofit: Retrofit) = retrofit.create(clazz<ApiServices>())

    companion object {

        @Volatile
        private var INSTANCE: AppModulesProvider? = null

        @JvmStatic
        fun getInstance(): AppModulesProvider {
            return INSTANCE ?: synchronized(clazz<AppModulesProvider>()) {
                return@synchronized AppModulesProvider()
            }.also {
                INSTANCE = it
            }
        }

    }
}