package com.example.newsapp.ui.main_activity.news_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.domain.model.enums.Tags
import com.example.newsapp.domain.use_case.GetNewsUseCase
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val useCase: GetNewsUseCase) : ViewModel() {

    private var _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var _newsWithGenres = MutableLiveData<List<NewWithGenre>>()
    val newsWithGenres: LiveData<List<NewWithGenre>> get() = _newsWithGenres

    fun getNews(country: Countries, tag: Tags) {
        viewModelScope.launch {
            val result = useCase.execute(country, tag)
            result.onEach {
                when (it) {
                    is Resource.Error -> {
                        _error.value = it.message
                        _isLoading.value = false
                    }

                    is Resource.Success -> {
                        it.data?.let { list ->
                            _newsWithGenres.value = list
                            if (list.isEmpty())
                                _error.value = it.message ?: "Server problem, list is empty"
                        }
                        _isLoading.value = false
                    }

                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                }
            }.collect()
        }
    }
}