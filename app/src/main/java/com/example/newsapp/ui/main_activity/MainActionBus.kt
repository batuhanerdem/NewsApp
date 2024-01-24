package com.example.newsapp.ui.main_activity

import com.example.newsapp.ui.base.BaseActionBus

sealed interface MainActionBus : BaseActionBus {
    data object Init: MainActionBus
}