package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.newsapp.databinding.HomeNewItemBinding
import com.example.newsapp.databinding.SearchNewItemBinding
import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.ui.adapter.view_holder.GeneralNewViewHolder
import com.example.newsapp.ui.adapter.view_holder.HomeNewViewHolder
import com.example.newsapp.ui.adapter.view_holder.SearchNewViewHolder

class NewAdapter(
    private val viewHolderType: ViewHolders,
    private val goToNew: (new: NewWithGenre) -> Unit
) : ListAdapter<NewWithGenre, GeneralNewViewHolder>(NewDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralNewViewHolder {
        return when (viewHolderType) {
            is ViewHolders.HomeNew -> {
                val binding =
                    HomeNewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeNewViewHolder(binding, goToNew)
            }

            ViewHolders.SearchNew -> {
                val binding =
                    SearchNewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SearchNewViewHolder(binding, goToNew)
            }
        }
    }

    override fun onBindViewHolder(holder: GeneralNewViewHolder, position: Int) {
        getItem(position).also {
            holder.bind(it)
        }
    }

}

object NewDiffCallback : DiffUtil.ItemCallback<NewWithGenre>() {
    override fun areItemsTheSame(oldItem: NewWithGenre, newItem: NewWithGenre): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NewWithGenre, newItem: NewWithGenre): Boolean {
        return oldItem.new.url == newItem.new.url
    }

}

sealed class ViewHolders {
    data object HomeNew : ViewHolders()
    data object SearchNew : ViewHolders()
}

