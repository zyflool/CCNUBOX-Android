package com.muxixyz.ccnubox.home.export

import com.muxixyz.ccnubox.home.data.domain.HomeUseCases

class HomeApi(private val homeUseCases: HomeUseCases): IHomeExportApi {

    override suspend fun getCarouselImages(): List<String> {
        return homeUseCases.getCarouselImages()
    }

}