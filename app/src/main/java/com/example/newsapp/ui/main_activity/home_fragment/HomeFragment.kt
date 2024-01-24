package com.example.newsapp.ui.main_activity.home_fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.ui.adapter.NewAdapter
import com.example.newsapp.ui.adapter.ViewHolders
import com.example.newsapp.ui.base.BaseFragment
import com.example.newsapp.ui.main_activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeActionBus, HomeViewModel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate, HomeViewModel::class.java
) {
    private lateinit var adapter: NewAdapter
    override val viewModel by viewModels<HomeViewModel>()
    override fun initPage() {
    }

    override fun onResume() {
        super.onResume()
        setRV()
        setOnClickListeners()
        viewModel.setCurrentCountry()
        viewModel.setNews()
    }

    override suspend fun onAction(action: HomeActionBus) {
        when (action) {
            HomeActionBus.Init -> {}
            HomeActionBus.Loading -> progressBar.show()
            is HomeActionBus.NewsLoaded -> {
                adapter.submitList(action.list.toList())
                progressBar.hide()
            }

            is HomeActionBus.ShowErrorMessage -> {
                progressBar.hide()
                showErrorMessage(action.errorMessage)

            }

            is HomeActionBus.SingleNewLoaded -> {
                setSingleNew(action.new)
//                progressBar.hide()
            }

        }
    }

    private fun setRV() {
        adapter = NewAdapter(ViewHolders.HomeNew) { newWithGenre ->
            goToNewFragmentWithNew(newWithGenre)
        }
        binding.rvRecentNews.adapter = adapter
    }

    private fun setSingleNew(new: NewWithGenre) {
        setSingleNewGlideAndUI(new)
        setSingleNewOnClick(new)
    }

    private fun setOnClickListeners() {
        binding.tvMore.setOnClickListener {
            val activity = requireActivity() as MainActivity
            activity.setNavigationItemId(R.id.searchFragment)
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            onResume()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setSingleNewOnClick(new: NewWithGenre) {
        binding.ivNew.setOnClickListener {
            goToNewFragmentWithNew(new)
        }
    }

    private fun setSingleNewGlideAndUI(new: NewWithGenre) {
        val requestOptions = RequestOptions().transform(
            MultiTransformation(
                CenterCrop(), RoundedCornersTransformation(
                    60, 0, RoundedCornersTransformation.CornerType.BOTTOM
                )
            )
        )

        Glide.with(requireContext())
            .load(new.new.image.toUri())
            .apply(requestOptions)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    // Resim yükleme başarısız olduğunda yapılacak işlemler
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.hide()
                    return false
                }
            })
            .into(binding.ivNew)
        binding.tvTitle.text = new.new.name
    }

    private fun goToNewFragmentWithNew(newWithGenre: NewWithGenre) {
        val action = HomeFragmentDirections.actionHomeFragmentToNewFragment(newWithGenre)
        navigateTo(action)
    }

}