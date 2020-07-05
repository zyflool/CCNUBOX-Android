package com.muxixyz.ccnubox.home.ui

import android.os.Bundle
import com.muxixyz.ccnubox.home.export.HomeModule
import com.muxixyz.ccnubox.uikit.base.BaseActivity
import me.xx2bab.bro.annotations.BroActivity

@BroActivity("ccnubox://home", HomeModule::class)
class HomeActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}