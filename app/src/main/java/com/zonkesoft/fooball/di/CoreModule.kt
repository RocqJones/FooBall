package com.zonkesoft.fooball.di

import com.zonkesoft.fooball.core.utils.AndroidNetworkConnectivityObserver
import com.zonkesoft.fooball.core.utils.NetworkConnectivityObserver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    // Network connectivity observer
    single<NetworkConnectivityObserver> {
        AndroidNetworkConnectivityObserver(androidContext())
    }
}