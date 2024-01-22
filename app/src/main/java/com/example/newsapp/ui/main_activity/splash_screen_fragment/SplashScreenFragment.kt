package com.example.newsapp.ui.main_activity.splash_screen_fragment

import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSplashScreenBinding
import com.example.newsapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashScreenFragment :
    BaseFragment<SplashScreenActionBus, SplashScreenViewModel, FragmentSplashScreenBinding>(
        FragmentSplashScreenBinding::inflate, SplashScreenViewModel::class.java
    ) {
    override suspend fun onAction(action: SplashScreenActionBus) {
        when (action) {
            SplashScreenActionBus.DataLoaded -> {
                coroutineScope {
                    delay(1700)
                    val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment()
                    val thisFragmentId = R.id.splashScreenFragment
                    navigateTo(action, thisFragmentId,true)
                }
            }
            is SplashScreenActionBus.ShowErrorMessage -> showErrorMessage(action.errorMessage)
            SplashScreenActionBus.Init -> {}
        }
    }

    override fun initPage() {
        viewModel.getCountryFromDataStore()
    }


}