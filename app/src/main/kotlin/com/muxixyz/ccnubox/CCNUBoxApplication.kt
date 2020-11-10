package com.muxixyz.ccnubox

import android.app.Application
import android.content.Context
import android.content.Intent
import com.muxixyz.ccnubox.home.export.homeKoinProvider
import com.muxixyz.ccnubox.iokit.export.ioKitKoinProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class CCNUBoxApplication: Application() {

    override fun onCreate() {
        super.onCreate()

//        initBro()
        initKoin()

    }

    private fun initKoin() {
        startKoin{
            androidLogger()
            androidContext(this@CCNUBoxApplication)
            androidFileProperties()
            modules(homeKoinProvider,
                ioKitKoinProvider)
        }
    }
//    private fun initBro() {
//        val interceptor: IBroInterceptor = object : IBroInterceptor {
//            override fun beforeFindActivity(
//                context: Context,
//                target: String,
//                intent: Intent,
//                properties: BroProperties?
//            ): Boolean {
//                return false
//            }
//
//            override fun beforeGetApi(
//                context: Context,
//                target: String,
//                api: IBroApi,
//                properties: BroProperties?
//            ): Boolean {
//                return false
//            }
//
//            override fun beforeGetModule(
//                context: Context,
//                target: String,
//                module: IBroModule,
//                properties: BroProperties?
//            ): Boolean {
//                return false
//            }
//
//            override fun beforeStartActivity(
//                context: Context,
//                target: String,
//                intent: Intent,
//                properties: BroProperties?
//            ): Boolean {
//                return false
//            }
//
//        }
//        val monitor: IBroMonitor = object : IBroMonitor {
//            override fun onModuleException(errorCode: Int) {}
//            override fun onActivityRudderException(errorCode: Int, builder: Builder) {
//
//            }
//
//            override fun onApiException(errorCode: Int) {}
//        }
//        val broBuilder = BroBuilder()
////            .setDefaultActivity(SampleDefaultActivity::class.java)
//            .setLogEnable(true)
//            .setMonitor(monitor)
//            .setInterceptor(interceptor)
//        Bro.initialize(this, broBuilder)
//    }
}