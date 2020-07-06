package com.muxixyz.ccnubox.home.data.domain

import com.muxixyz.ccnubox.home.data.repo.HomeLocalRepo
import com.muxixyz.ccnubox.home.data.repo.HomeRemoteRepo
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.TimeUnit

class HomeUseCases: KoinComponent {

    val oneHour = TimeUnit.HOURS.toMillis(1)
    val localCache: HomeLocalRepo by inject()
    val remoteApi: HomeRemoteRepo by inject()


    suspend fun getCarouselImages(): List<String> {
        if (localCache.lastCommittedTimestamp!! + oneHour <= System.currentTimeMillis()) {
            return localCache.carouselImages!!.toList()
        }
        return remoteApi.getCarouselImages()
    }

}