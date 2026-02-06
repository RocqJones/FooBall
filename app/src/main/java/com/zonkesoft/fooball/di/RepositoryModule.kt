package com.zonkesoft.fooball.di

import com.zonkesoft.fooball.data_source.repository.PredictionsRepository
import com.zonkesoft.fooball.data_source.repository.PredictionsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<PredictionsRepository> { PredictionsRepositoryImpl(get()) }
}