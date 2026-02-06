package com.zonkesoft.fooball.di

import com.zonkesoft.fooball.ui.viewmodel.AnalysisViewModel
import com.zonkesoft.fooball.ui.viewmodel.FixturesIngestViewModel
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
    viewModel { HomeViewModel() }
    viewModel { OfflineViewModel(get()) }
    viewModel { TodayPredictionsViewModel(get()) }
    viewModel { AnalysisViewModel(get()) }
    viewModel { TopPicksViewModel(get()) }
    viewModel { FixturesIngestViewModel(get()) }
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