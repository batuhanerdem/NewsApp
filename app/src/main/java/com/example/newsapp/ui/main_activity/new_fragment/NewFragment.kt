package com.example.newsapp.ui.main_activity.new_fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.FragmentNewBinding
import com.example.newsapp.domain.model.New
import com.example.newsapp.ui.main_activity.MainActivity
import com.example.newsapp.ui.main_activity.MainActivityViewModel

class NewFragment : Fragment() {
    private lateinit var binding: FragmentNewBinding
    private lateinit var activityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityViewModel = (activity as MainActivity).activityViewModel
        val selectedNew = activityViewModel.getSelectedNew()
        selectedNew?.let {
            setUI(it)
        }
    }

    private fun setUI(new: New) {
        with(binding) {
            val imageUri = new.image.toUri()
            Glide.with(requireActivity()).load(imageUri).into(ivNew)
            tvTitle.text = new.name
            tvNewText.text = new.description
            tvSource.text = new.source
            tvUrl.setOnClickListener {
                val uri = Uri.parse(new.url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }
    }
}