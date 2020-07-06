package com.muxixyz.ccnubox.home.data.repo

import android.content.Context
import com.muxixyz.android.iokit.preference.Preferences

class HomeLocalRepo(context: Context): Preferences(context, "home") {

    var carouselImages by stringSetPref(defaultValue = setOf())
    var lastCommittedTimestamp by longPref(defaultValue = 0L)

}