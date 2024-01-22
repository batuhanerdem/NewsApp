package com.example.newsapp.ui.adapter.view_holder

import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.SearchNewItemBinding
import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.utils.DateUtils.formatDateRelativeToToday

class SearchNewViewHolder(
    private val binding: SearchNewItemBinding,
    private val goToNew: (new: NewWithGenre) -> Unit
) :
    GeneralNewViewHolder(binding.root) {
    override fun bind(new: NewWithGenre) {
        val imageUri = new.new.image.toUri()
        val title = new.new.name
        val date = new.new.date.formatDateRelativeToToday()
        val context = this.itemView.context

        binding.apply {
            Glide.with(context).load(imageUri).into(ivNew)
            tvTitle.text = title
            tvDate.text = date
            ivNew.setOnClickListener {
                goToNew(new)
            }
        }
    }

}
