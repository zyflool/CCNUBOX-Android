package com.muxixyz.ccnubox.home.data.repo

import com.muxixyz.ccnubox.iokit.network.RetrofitClients
import retrofit2.http.GET
import java.lang.Exception

class HomeRemoteRepo(private val retrofitClients: RetrofitClients) {

    private val homeApi = retrofitClients.generalClient.create(IHomeApi::class.java)

//    suspend fun getCarouselImages() = homeApi.getCarouselImages()

    suspend fun getCarouselImages(): List<String> {
        return try {
            // Put some pre process logic here, or try-catch the service error scenarios
            homeApi.getCarouselImages()
        } catch (e: Exception) {
            e.printStackTrace()
            listOf()
        }
    }

    internal interface IHomeApi {
        @GET("home/carousel")
        suspend fun getCarouselImages(): List<String>
    }

}