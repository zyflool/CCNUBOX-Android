package com.muxixyz.ccnubox.home.export

import com.muxixyz.ccnubox.home.data.domain.HomeUseCases
import com.muxixyz.ccnubox.home.data.repo.HomeLocalRepo
import com.muxixyz.ccnubox.home.data.repo.HomeRemoteRepo
import com.muxixyz.ccnubox.home.ui.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val homeKoinProvider: Module = module {

    single { HomeLocalRepo(androidApplication()) }

    single { HomeRemoteRepo(get()) }

    single { HomeUseCases() }

    viewModel { HomeViewModel(get()) }

    single<IHomeExportApi> { HomeApi(get()) }

}