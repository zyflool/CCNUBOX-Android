package com.muxixyz.ccnubox.home.export

import android.content.Context
import me.xx2bab.bro.annotations.BroModule
import me.xx2bab.bro.common.IBroApi
import me.xx2bab.bro.common.IBroModule

@BroModule
class HomeModule: IBroModule {

    override fun getLaunchDependencies(): Set<Class<out IBroApi>>? {
        return null
    }

    override fun onCreate(context: Context?) {

    }

}