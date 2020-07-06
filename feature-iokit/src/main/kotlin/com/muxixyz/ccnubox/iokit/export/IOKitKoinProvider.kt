package com.muxixyz.ccnubox.iokit.export

import com.muxixyz.ccnubox.iokit.network.RetrofitClients
import org.koin.dsl.module

val ioKitKoinProvider = module {

    single { RetrofitClients() }

}