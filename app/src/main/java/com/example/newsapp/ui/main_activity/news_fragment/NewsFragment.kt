package com.example.newsapp.ui.main_activity.news_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.domain.model.New
import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.domain.model.enums.Tags
import com.example.newsapp.ui.adapter.NewAdapter
import com.example.newsapp.ui.main_activity.holder_fragment.HolderFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment(
    private val tag: Tags = Tags.GENERAL,
    private val country: Countries = Countries.TURKEY
) : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val fragmentViewModel: NewsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentViewModel.news.observe(viewLifecycleOwner) {
            setRV(it)
        }

        fragmentViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                it ?: "Something went wrong, please try again later",
                Toast.LENGTH_LONG
            ).show()
        }

        fragmentViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) setProgressBar() else hideProgressBar()
        }
        fragmentViewModel.getNews(country, tag)
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
        binding.recyclerViewNew.visibility = View.VISIBLE
    }

    private fun setProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewNew.visibility = View.GONE
    }

    private fun setRV(newList: List<New>) {
        val adapter = NewAdapter(newList) { new: New ->
            val action = HolderFragmentDirections.actionHolderFragmentToNewFragment()
            val navController = requireParentFragment().findNavController()
            action.arguments.putSerializable("new", new)
            navController.navigate(action)

        }
        binding.recyclerViewNew.adapter = adapter
        binding.recyclerViewNew.layoutManager = LinearLayoutManager(context)
    }
}