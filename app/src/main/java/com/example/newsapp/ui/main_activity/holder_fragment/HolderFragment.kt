package com.example.newsapp.ui.main_activity.holder_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentHolderBinding
import com.example.newsapp.domain.model.New
import com.example.newsapp.domain.model.enums.Tags
import com.example.newsapp.ui.adapter.FragmentAdapter
import com.example.newsapp.ui.adapter.NewAdapter
import com.example.newsapp.ui.main_activity.MainActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HolderFragment : Fragment() {
    private lateinit var binding: FragmentHolderBinding
    private val viewModel: HolderViewModel by viewModels()
    private lateinit var adapter: NewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHolderBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val hostingActivity = requireActivity() as MainActivity
        hostingActivity.setSelectedFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout()
        setOnListChangeListener()
        setSearchListener()
        setLoadingListener()
    }

    private fun setLayout() {
        //view pager and table layout
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val tags = enumValues<Tags>().toList()
        val fragmentAdapter = FragmentAdapter(this.childFragmentManager, this.lifecycle, tags)
        viewPager.adapter = fragmentAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tags[position].title
        }.attach()

        //recycler view for searching results
        adapter = NewAdapter { new -> goToNewFragmentWithNew(new) }
        binding.rvSearch.adapter = adapter
        binding.rvSearch.layoutManager = LinearLayoutManager(context)

    }

    private fun goToNewFragmentWithNew(new: New) {
        val action = HolderFragmentDirections.actionHolderFragmentToNewFragment(new)
        val navController = findNavController()
        navController.navigate(action)
    }

    private fun setSearchListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) makeRvInvisible()
                else makeRvVisible()
                viewModel.filterList(newText)
                return true
            }

        })
    }

    private fun setOnListChangeListener() {
        viewModel.searchingList.observe(viewLifecycleOwner) {
            adapter.submitList(it.toList())
        }
    }

    private fun setLoadingListener() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            println(binding.loadingBar.isVisible)
            binding.loadingBar.isVisible = it
        }
    }

    private fun makeRvVisible() {
        binding.tabLayout.isGone = true
        binding.viewPager.isGone = true
        binding.rvSearch.isVisible = true
    }

    private fun makeRvInvisible() {
        binding.tabLayout.isVisible = true
        binding.viewPager.isVisible = true
        binding.rvSearch.isGone = true
    }

}