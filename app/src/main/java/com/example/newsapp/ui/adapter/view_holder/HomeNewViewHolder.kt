package com.example.newsapp.ui.adapter.view_holder

import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.HomeNewItemBinding
import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.utils.DateUtils.formatDateRelativeToToday

class HomeNewViewHolder(
    private val binding: HomeNewItemBinding,
    private val goToNew: (new: NewWithGenre) -> Unit
) : GeneralNewViewHolder(binding.root) {

    override fun bind(newItem: NewWithGenre) {
        val imageUri = newItem.new.image.toUri()
        val title = newItem.new.name
        val date = newItem.new.date.formatDateRelativeToToday()
        val source = newItem.new.source
        val context = this.itemView.context

        binding.apply {
            Glide.with(context).load(imageUri).into(ivNew)
            tvTitle.text = title
            tvDate.text = date
            ivNew.setOnClickListener {
                goToNew(newItem)
            }
            tvSource.text = source
        }
    }

}