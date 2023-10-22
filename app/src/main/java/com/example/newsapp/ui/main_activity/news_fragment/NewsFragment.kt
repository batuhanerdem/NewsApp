package com.example.newsapp.ui.main_activity.news_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.domain.model.APIResponse
import com.example.newsapp.domain.model.New
import com.example.newsapp.ui.adapter.NewAdapter
import com.example.newsapp.ui.main_activity.MainActivity
import com.example.newsapp.ui.main_activity.MainActivityViewModel
import com.example.newsapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding // degistir
    private val fragmentViewModel: NewsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val observer = Observer<Resource<APIResponse>> {
            when (it) {
                is Resource.Loading -> {
                    setProgressBar()
                }

                is Resource.Success -> {
                    val data = it.data?.result
                    data?.let { list ->
                        setRV(list)
                    }
                    hideProgressBar()
                }

                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        "Something went wrong, please try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        fragmentViewModel.getNews()
        fragmentViewModel.news.observe(viewLifecycleOwner, observer)
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
            val action = NewsFragmentDirections.actionHomeFragmentToNewFragment()
            action.arguments.putSerializable("new", new)
            view?.findNavController()?.navigate(action)
        }
        binding.recyclerViewNew.adapter = adapter
        binding.recyclerViewNew.layoutManager = LinearLayoutManager(context)
    }
}