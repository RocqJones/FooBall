package com.zonkesoft.fooball.di

import com.zonkesoft.fooball.ui.viewmodel.HomeViewModel
import com.zonkesoft.fooball.ui.viewmodel.OfflineViewModel
import com.zonkesoft.fooball.ui.viewmodel.TodayPredictionsViewModel
import com.zonkesoft.fooball.ui.viewmodel.TopPicksViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Aggregated Koin modules in our app.
 */
val appModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { OfflineViewModel(get()) }
    viewModel { TodayPredictionsViewModel(get()) }
    viewModel { TopPicksViewModel(get()) }
}


/**
 * All Koin modules in our app.
 */
val appModules = listOf(
    appModule,
    coreModule,
    repositoryModule,
    networkModule
)