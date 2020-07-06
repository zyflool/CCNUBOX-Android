package com.muxixyz.ccnubox.home.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muxixyz.ccnubox.home.data.domain.HomeUseCases
import kotlinx.coroutines.launch

class HomeViewModel(private val homeUseCases: HomeUseCases): ViewModel() {

    val imageListLD = MutableLiveData<List<String>>()
    val isLoadingLD = MutableLiveData(false)

    fun refreshCarousel() {
        isLoadingLD.value = true
        viewModelScope.launch {
            try {
                val res = homeUseCases.getCarouselImages()
                // You may ned to do some model transform for UI here
                // ...
                imageListLD.postValue(res)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoadingLD.postValue(false)
            }
        }
    }

}