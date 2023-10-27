package com.example.newsapp.ui.main_activity.new_fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.FragmentNewBinding
import com.example.newsapp.domain.model.New
import com.example.newsapp.utils.Constants.Companion.formatDate
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class NewFragment : Fragment() {
    private lateinit var binding: FragmentNewBinding
    private val args: NewFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedNew = args.New
        setUI(selectedNew)
    }

    private fun setUI(new: New) {
        with(binding) {
            val imageUri = new.image.toUri()
            Glide.with(requireActivity()).load(imageUri).into(ivNew)
            tvTitle.text = new.name
            tvNewText.text = new.description
            tvSource.text = new.source
            tvDate.text = new.date.formatDate()
            tvUrl.setOnClickListener {
                val uri = Uri.parse(new.url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }
    }
}