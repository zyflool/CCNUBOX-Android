package com.muxixyz.ccnubox.timetable.export

import android.app.Application
import androidx.room.Room
import com.muxixyz.ccnubox.timetable.data.database.CourseDao
import com.muxixyz.ccnubox.timetable.data.database.CourseDatabase
import com.muxixyz.ccnubox.timetable.data.database.TimetableLocalRepo
import com.muxixyz.ccnubox.timetable.data.network.TimetableRemoteRepo
import com.muxixyz.ccnubox.timetable.data.repository.TimetableRepository
import com.muxixyz.ccnubox.timetable.ui.TimetableViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val timetableKoinProvider: Module = module {

    viewModel { TimetableViewModel(get()) }

    single<ITimetableExportApi> { TimetableApi(get()) }

    //network
    single { TimetableRemoteRepo(get()) }

    // database
    fun provideCourseDatabase(application: Application) :CourseDatabase {
        return Room.databaseBuilder(application, CourseDatabase::class.java, "course").build()
    }

    fun provideCourseDao(database: CourseDatabase): CourseDao {
        return database.courseDao()
    }

    single { provideCourseDao(get()) }

    single { provideCourseDatabase(androidApplication()) }

    single { TimetableLocalRepo(androidApplication(), get()) }

    single { TimetableRepository(get(), get()) }
}