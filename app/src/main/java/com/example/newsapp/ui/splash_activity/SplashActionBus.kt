package com.example.newsapp.ui.splash_activity

import com.example.newsapp.ui.base.BaseActionBus

sealed interface SplashActionBus : BaseActionBus {

    data object Init : SplashActionBus

    data object DataLoaded : SplashActionBus
}