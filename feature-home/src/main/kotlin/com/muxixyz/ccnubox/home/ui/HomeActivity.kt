package com.muxixyz.ccnubox.home.ui

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.muxixyz.ccnubox.home.export.HomeModule
import com.muxixyz.ccnubox.uikit.base.BaseActivity
import me.xx2bab.bro.annotations.BroActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

@BroActivity("ccnubox://home", HomeModule::class)
class HomeActivity: BaseActivity() {

    val model : HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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