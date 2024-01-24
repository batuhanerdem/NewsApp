package com.example.newsapp.ui.splash_activity

import android.annotation.SuppressLint
import com.example.newsapp.databinding.ActivitySplashBinding
import com.example.newsapp.ui.base.BaseActivity
import com.example.newsapp.ui.main_activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity :
    BaseActivity<SplashActionBus, SplashViewModel, ActivitySplashBinding>(
        ActivitySplashBinding::inflate,
        SplashViewModel::class.java
    ) {

    override fun init() {
        viewModel.getCountryFromDataStore()
    }

    override suspend fun onAction(action: SplashActionBus) {
        when (action) {
            SplashActionBus.DataLoaded -> {
                coroutineScope {
                    delay(1700)
                    goTo(MainActivity::class.java)
                    this@SplashActivity.finish()
                }
            }

            SplashActionBus.Init -> {}
        }
    }
}