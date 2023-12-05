package com.example.newsapp.ui.main_activity.holder_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.New
import com.example.newsapp.domain.use_case.GetAllNewsUseCase
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HolderViewModel @Inject constructor(private val getAllNewsUseCase: GetAllNewsUseCase) :
    ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    val searchingList = MutableLiveData<List<New>>()
    private var newList: List<New>? = null

    init {
        viewModelScope.launch {
            getAllNews()
        }
    }

    fun filterList(query: String?) {
        _isLoading.value = true
        viewModelScope.launch {
            if (query == null) return@launch
            newList?.let {
                val filteredList = mutableListOf<New>()
                for (new in it) {
                    if (new.name.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))) {
                        filteredList.add(new)
                    }
                }
                searchingList.value = filteredList
                _isLoading.value = false
            }

        }
    }


    private suspend fun getAllNews() {
        val result = getAllNewsUseCase.execute()
        result.onEach {
            when (it) {
                is Resource.Error -> _isLoading.value = false
                is Resource.Loading -> {
                    //nothing happens here, I don't want to trigger any loading component
                }
                is Resource.Success -> {
                    newList = it.data!!
                    _isLoading.value = false
                }
            }
        }.collect()
    }
}
