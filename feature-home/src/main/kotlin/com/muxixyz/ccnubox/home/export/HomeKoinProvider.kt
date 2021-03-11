package com.muxixyz.ccnubox.home.export

import android.app.Application
import androidx.room.Room
import com.muxixyz.ccnubox.home.data.database.HomeLocalRepo
import com.muxixyz.ccnubox.home.data.database.TodoDao
import com.muxixyz.ccnubox.home.data.database.TodoDatabase
import com.muxixyz.ccnubox.home.data.network.HomeRemoteRepo
import com.muxixyz.ccnubox.home.data.HomeRepository
import com.muxixyz.ccnubox.home.ui.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val homeKoinProvider: Module = module {

    viewModel { HomeViewModel(get()) }

    single<IHomeExportApi> { HomeApi(get()) }

    //network
    single { HomeRemoteRepo(get()) }

    // database

    fun provideTodoDao(application: Application): TodoDao {
        return  Room.databaseBuilder(application, TodoDatabase::class.java, "todo")
            .build().todoDao()
    }

    single { provideTodoDao(androidApplication()) }

    single { HomeLocalRepo(androidApplication(), get())}

    single { HomeRepository(get(), get()) }
}