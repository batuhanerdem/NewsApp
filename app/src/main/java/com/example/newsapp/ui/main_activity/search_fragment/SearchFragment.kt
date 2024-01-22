package com.example.newsapp.ui.main_activity.search_fragment

import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.example.newsapp.databinding.FragmentSearchBinding
import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.domain.model.enums.Tags
import com.example.newsapp.ui.adapter.FragmentAdapter
import com.example.newsapp.ui.adapter.NewAdapter
import com.example.newsapp.ui.adapter.ViewHolders
import com.example.newsapp.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchActionBus, SearchViewModel, FragmentSearchBinding>(
    FragmentSearchBinding::inflate, SearchViewModel::class.java
) {
    private lateinit var adapter: NewAdapter
    private lateinit var fragmentAdapter: FragmentAdapter
    private val tags = enumValues<Tags>().toList()

    override fun initPage() {
        setFragmentAdapter()
    }

    override fun onResume() {
        super.onResume()
        viewModel.setCurrentCountry()
        viewModel.setNews()
        setUI()
        setSearchListener()
        setOnClickListeners()
    }

    override suspend fun onAction(action: SearchActionBus) {
        when (action) {
            is SearchActionBus.FilteringFinished -> {
                adapter.submitList(action.filteredList.toList())
                progressBar.hide()
            }

            SearchActionBus.Init -> {}
            SearchActionBus.Loading -> progressBar.show()

            is SearchActionBus.ShowErrorMessage -> showErrorMessage(action.errorMessage)
        }
    }

    private fun setUI() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        viewPager.adapter = fragmentAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tags[position].title
        }.attach()

        adapter = NewAdapter(ViewHolders.SearchNew) { newWithGenre ->
            goToNewFragmentWithNew(newWithGenre)
        }
        binding.rvSearch.adapter = adapter
    }

    private fun setSearchListener() {
        binding.edtSearch.text.clear()
        binding.edtSearch.doOnTextChanged { text, _, _, _ ->
            changeRVVisibility(text.toString().isEmpty())
            viewModel.filterList(text.toString())
        }

    }

    private fun goToNewFragmentWithNew(newWithGenre: NewWithGenre) {
        val action = SearchFragmentDirections.actionSearchFragmentToNewFragment(newWithGenre)
        navigateTo(action)
    }

    private fun changeRVVisibility(visibility: Boolean) {
        binding.tabLayout.isVisible = visibility
        binding.viewPager.isVisible = visibility
        binding.rvSearch.isVisible = !visibility
    }

    private fun setFragmentAdapter() {
        fragmentAdapter = FragmentAdapter(
            this.childFragmentManager, this.lifecycle, tags
        )
    }

    private fun setOnClickListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            onResume()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

}