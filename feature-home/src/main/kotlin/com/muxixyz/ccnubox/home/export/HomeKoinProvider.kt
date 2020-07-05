package com.muxixyz.ccnubox.home.export

import com.muxixyz.ccnubox.home.data.domain.HomeUseCases
import com.muxixyz.ccnubox.home.data.repo.HomeLocalCache
import com.muxixyz.ccnubox.home.data.repo.HomeRemoteApi
import com.muxixyz.ccnubox.home.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val HomeKoinProvider: Module = module {

    single { HomeLocalCache() }

    single { HomeRemoteApi() }

    single { HomeUseCases() }

    viewModel { HomeViewModel(get()) }

}