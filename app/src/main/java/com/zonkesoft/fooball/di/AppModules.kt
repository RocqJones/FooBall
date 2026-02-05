package com.zonkesoft.fooball.di

import com.zonkesoft.fooball.ui.viewmodel.HomeViewModel
import com.zonkesoft.fooball.ui.viewmodel.OfflineViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Aggregated Koin modules in our app.
 */
val appModule = module {
    // ViewModels
    viewModel { HomeViewModel() }
    viewModel { OfflineViewModel(get()) }
}


/**
 * All Koin modules in our app.
 */
val appModules = listOf(
    appModule,
    coreModule,
    repositoryModule,
    /*networkModule,
    databaseModule */
)