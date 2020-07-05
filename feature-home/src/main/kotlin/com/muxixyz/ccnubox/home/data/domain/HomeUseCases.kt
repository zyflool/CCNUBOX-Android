package com.muxixyz.ccnubox.home.data.domain

import com.muxixyz.ccnubox.home.data.repo.HomeLocalCache
import com.muxixyz.ccnubox.home.data.repo.HomeRemoteApi
import org.koin.core.KoinComponent
import org.koin.core.inject

class HomeUseCases: KoinComponent {

    val localCache: HomeLocalCache by inject()
    val remoteApi: HomeRemoteApi by inject()

}