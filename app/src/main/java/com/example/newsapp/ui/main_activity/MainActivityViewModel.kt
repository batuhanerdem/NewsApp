package com.example.newsapp.ui.main_activity

import androidx.lifecycle.ViewModel
import com.example.newsapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
) : BaseViewModel<MainActionBus>() {


}