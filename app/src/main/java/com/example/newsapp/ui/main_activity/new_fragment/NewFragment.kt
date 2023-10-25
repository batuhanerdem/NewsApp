package com.example.newsapp.ui.main_activity.new_fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.net.toUri
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewBinding
import com.example.newsapp.domain.model.New
import com.example.newsapp.ui.main_activity.news_fragment.NewsFragmentDirections
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class NewFragment : Fragment() {
    private lateinit var binding: FragmentNewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedNew = arguments?.getSerializable("new")!! as New
        setUI(selectedNew)
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            val action = NewFragmentDirections.actionNewFragmentToHolderFragment()
//            view.findNavController().navigate(action)
//
//        }
    }

    private fun setUI(new: New) {
        with(binding) {
            val imageUri = new.image.toUri()
            Glide.with(requireActivity()).load(imageUri).into(ivNew)
            tvTitle.text = new.name
            tvNewText.text = new.description
            tvSource.text = new.date.formatDate()
            println(new.date.formatDate())
            tvUrl.setOnClickListener {
                val uri = Uri.parse(new.url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }
    }

    private fun String.formatDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        return try {
            val date = dateFormat.parse(this)
            val outputFormat = SimpleDateFormat("EEEE, HH:mm:ss, yyyy", Locale.getDefault())
            outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            "Invalid Date"
        }
    }

}