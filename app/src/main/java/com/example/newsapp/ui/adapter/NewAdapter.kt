 package com.example.newsapp.ui.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.RecyclerListItemBinding
import com.example.newsapp.domain.model.New
import com.example.newsapp.utils.DateUtils.formatDateRelativeToToday

class NewAdapter(
    private val callBack: (new: New) -> Unit
) : ListAdapter<New, NewAdapter.VHMainList>(NewDiffCallback) {
    class VHMainList(val binding: RecyclerListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHMainList {
        val binding = RecyclerListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VHMainList(binding)
    }

    override fun onBindViewHolder(holder: VHMainList, position: Int) {
        val currentNew = getItem(position)
        val imageUri = currentNew.image.toUri()
        val title = currentNew.name
        val source = currentNew.source
        val date = currentNew.date.formatDateRelativeToToday()
        holder.binding.apply {
            Glide.with(holder.itemView.context).load(imageUri).into(ivNew)
            ivNew.setColorFilter(
                Color.HSVToColor(80, floatArrayOf(0f, 0f, 0f)),
                PorterDuff.Mode.DARKEN
            )
            tvTitle.text = title
            tvSource.text = source
            tvDate.text = date
        }

        holder.binding.ivNew.setOnClickListener {
            callBack(currentNew)
        }
    }
}

object NewDiffCallback : DiffUtil.ItemCallback<New>() {
    override fun areItemsTheSame(oldItem: New, newItem: New): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: New, newItem: New): Boolean {
        return oldItem.url == newItem.url
    }
}