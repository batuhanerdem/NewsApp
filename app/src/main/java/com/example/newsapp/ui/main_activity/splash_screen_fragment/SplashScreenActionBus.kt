package com.example.newsapp.ui.main_activity.splash_screen_fragment

import com.example.newsapp.ui.base.BaseActionBus

sealed interface SplashScreenActionBus : BaseActionBus {

    data object Init : SplashScreenActionBus

    data class ShowErrorMessage(val errorMessage: String? = null) : SplashScreenActionBus

    data object DataLoaded : SplashScreenActionBus
}