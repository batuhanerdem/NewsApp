package com.example.newsapp.ui.main_activity.new_fragment

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.FragmentNewBinding
import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.utils.DateUtils.formatDateRelativeToToday
import com.example.newsapp.utils.StringUtils.getFirstSentence

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
        val selectedNewWithGenre = args.NewWithGenre
        setUI(selectedNewWithGenre)
        setOnClickListeners()
    }

    private fun setUI(newWithGenre: NewWithGenre) {
        binding.apply {
            val imageUri = newWithGenre.new.image.toUri()
            Glide.with(requireActivity()).load(imageUri).into(ivNew)
            ivNew.setColorFilter(
                Color.HSVToColor(60, floatArrayOf(0f, 0f, 0f)),
                PorterDuff.Mode.DARKEN
            )
            tvTitle.text = newWithGenre.new.name
            tvNewText.text = newWithGenre.new.description.repeat(10)
            tvSource.text = newWithGenre.new.source
            tvDate.text = newWithGenre.new.date.formatDateRelativeToToday()
            tvGenre.text = newWithGenre.genre
            tvFirstSentence.text = newWithGenre.new.description.getFirstSentence()
            tvView.text = "123"
        }
    }

    private fun setOnClickListeners() {
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}