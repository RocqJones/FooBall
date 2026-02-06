package com.zonkesoft.fooball.di

import com.google.gson.GsonBuilder
import com.zonkesoft.fooball.BuildConfig
import com.zonkesoft.fooball.core.constants.Constants
import com.zonkesoft.fooball.data_source.remote.api.FooBallApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {
        GsonBuilder().create()
    }

    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(Constants.Network.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.Network.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.Network.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single {
        get<Retrofit>().create(FooBallApiService::class.java)
    }
}