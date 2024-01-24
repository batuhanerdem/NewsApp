package com.example.newsapp.ui.main_activity.news_fragment

import android.os.Build
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.domain.model.enums.Tags
import com.example.newsapp.ui.adapter.NewAdapter
import com.example.newsapp.ui.adapter.ViewHolders
import com.example.newsapp.ui.base.BaseFragment
import com.example.newsapp.ui.main_activity.search_fragment.SearchFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment() :
    BaseFragment<NewsActionBus, NewsViewModel, FragmentNewsBinding>(
        FragmentNewsBinding::inflate, NewsViewModel::class.java
    ) {
    private lateinit var adapter: NewAdapter

    private var tag: Tags? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tag = if (Build.VERSION.SDK_INT >= 33)
            arguments?.getParcelable("tag", Tags::class.java)
        else {
            arguments?.getParcelable("tag")
        }
    }

    override fun initPage() {
    }

    override fun onResume() {
        super.onResume()
        setRV()
        viewModel.setTag(tag)
        viewModel.setCurrentCountry()
        viewModel.setNews()
    }

    override suspend fun onAction(action: NewsActionBus) {
        when (action) {
            NewsActionBus.Init -> {}
            NewsActionBus.Loading -> progressBar.show()
            is NewsActionBus.NewsLoaded -> {
                adapter.submitList(action.newList.toList())
                progressBar.hide()
            }

            is NewsActionBus.ShowErrorMessage -> {
                showErrorMessage(action.errorMessage)
                progressBar.hide()
            }
        }
    }

    private fun setRV() {
        adapter =
            NewAdapter(ViewHolders.SearchNew) { newWithGenre ->
                goToNewFragmentWithNew(
                    newWithGenre
                )
            }
        val recyclerView = binding.recyclerViewNew
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun goToNewFragmentWithNew(newWithGenre: NewWithGenre) {
        val action = SearchFragmentDirections.actionSearchFragmentToNewFragment(newWithGenre)
        val navController = requireParentFragment().findNavController()
        navController.navigate(action)
    }

}