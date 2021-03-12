package com.muxixyz.ccnubox.home.export

import androidx.room.Room
import com.muxixyz.ccnubox.home.data.HomeRepository
import com.muxixyz.ccnubox.home.data.database.HomeLocalRepo
import com.muxixyz.ccnubox.home.data.database.TodoDatabase
import com.muxixyz.ccnubox.home.data.network.HomeRemoteRepo
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

//    fun provideTodoDatabase(application: Application) :TodoDatabase {
//        return Room.databaseBuilder(application, TodoDatabase::class.java, "todo").build()
//    }
//
//    fun provideTodoDao(database: TodoDatabase): TodoDao {
//        return database.todoDao()
//    }
//
//    single { provideTodoDao(get()) }
//
//    single { provideTodoDatabase(androidApplication()) }
//
    single {
        Room.databaseBuilder(androidApplication(), TodoDatabase::class.java, "todo")
            .build()
    }
    single { get<TodoDatabase>().todoDao() }

    single { HomeLocalRepo(androidApplication(), get())}

    single { HomeRepository(get(), get()) }
}