package com.example.newsapp.ui.main_activity.splash_screen_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSplashScreenBinding
import com.example.newsapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment :
    BaseFragment<SplashScreenActionBus, SplashScreenViewModel, FragmentSplashScreenBinding>(
        FragmentSplashScreenBinding::inflate, SplashScreenViewModel::class.java
    ) {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("tag", "onCreate of splash: ")
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("tag", "onViewCreated: ")

    }

    override suspend fun onAction(action: SplashScreenActionBus) {
        when (action) {
            SplashScreenActionBus.DataLoaded -> {
//                if(viewModel.isBacked)
//                    this.onDestroy()
                coroutineScope {
                    Log.d("tag", "onAction: 1")
                    delay(1700)
                    val navigationAction =
                        SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment()
                    val thisFragmentId = R.id.splashScreenFragment
                    val homeFragmentId = R.id.homeFragment
                    navigateTo(navigationAction, thisFragmentId, true)
//                    findNavController().popBackStack(homeFragmentId, false)
                    viewModel.isBacked = true
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