package com.example.newsapp.ui.main_activity

import androidx.lifecycle.ViewModel
import com.example.newsapp.domain.model.New

class MainActivityViewModel : ViewModel() {

    private var selectedNew: New? = null

    fun getSelectedNew(): New? {
        return selectedNew
    }

    fun setSelectedNew(new: New) {
        selectedNew = new
    }
}