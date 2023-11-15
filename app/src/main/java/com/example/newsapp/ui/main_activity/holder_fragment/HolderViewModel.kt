package com.example.newsapp.ui.main_activity.holder_fragment

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

    val searchingList = MutableLiveData<List<New>>()
    private lateinit var newList: List<New>

    init {
        viewModelScope.launch {
            getAllNews()
        }
    }

    fun filterList(query: String?) {
        if (query == null) return
        val filteredList = mutableListOf<New>()
        for (new in newList) {
            if (new.name.lowercase(Locale.ROOT).contains(query)) {
                filteredList.add(new)
            }
        }
        searchingList.value = filteredList
    }

    private suspend fun getAllNews() {
        val result = getAllNewsUseCase.execute()
        result.onEach {
            when (it) {
                is Resource.Error -> TODO()
                is Resource.Loading -> TODO()
                is Resource.Success -> newList = it.data!!
            }
        }.collect()
    }
}
