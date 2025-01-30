
package com.example.swipe.DI

import com.example.swipe.Networking.ProductApiService
import com.example.swipe.Repository.ProductRepository
import com.example.swipe.ViewModel.ProductViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://app.getswipe.in/api/public/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(ProductApiService::class.java)
    }
}

val repositoryModule = module {
    single { ProductRepository(get(), androidContext()) }
}

val viewModelModule = module {
    viewModel { ProductViewModel(get()) }
}

val appModule = listOf(networkModule, repositoryModule, viewModelModule)