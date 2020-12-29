package com.muxixyz.ccnubox.schedule.export

import com.muxixyz.ccnubox.schedule.ui.ScheduleFragment
import org.koin.core.module.Module
import org.koin.dsl.module

val scheduleKoinProvider: Module = module {
    single<IScheduledFragment> { ScheduleFragment() }
}
