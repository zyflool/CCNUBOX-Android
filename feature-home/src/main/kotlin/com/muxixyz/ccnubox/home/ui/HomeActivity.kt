package com.muxixyz.ccnubox.home.ui

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.muxixyz.ccnubox.home.R
import com.muxixyz.ccnubox.home.databinding.HomeActivityBinding
import com.muxixyz.ccnubox.uikit.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity: BaseActivity() {

    val model : HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<HomeActivityBinding>(this, R.layout.home_activity)
//        binding.loadingView
        model.imageListLD.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                Toast.makeText(this@HomeActivity, "Error when loading Carousel.", Toast.LENGTH_SHORT).show()
            } else {
//            adapter.refresh(it)
            }
        })

        model.refreshCarousel()
    }

}