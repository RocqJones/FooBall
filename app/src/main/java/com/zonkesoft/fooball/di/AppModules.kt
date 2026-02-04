package com.zonkesoft.fooball.di

import com.zonkesoft.fooball.ui.viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Aggregated Koin modules in our app.
 */
val appModule = module {
    // ViewModels
    viewModel { HomeViewModel() }
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