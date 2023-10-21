package com.example.newsapp.ui.main_activity.news_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.APIResponse
import com.example.newsapp.domain.use_case.GetNewsUseCase
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val useCase: GetNewsUseCase) : ViewModel() {

    private val _news = MutableLiveData<Resource<APIResponse>>()
    val news: LiveData<Resource<APIResponse>> get() = _news

    fun getNews(country: String, tag: String) {
        _news.postValue(Resource.Loading())
        viewModelScope.launch {
            val result = useCase.execute(country, tag)
            _news.postValue(result)
        }
    }

}