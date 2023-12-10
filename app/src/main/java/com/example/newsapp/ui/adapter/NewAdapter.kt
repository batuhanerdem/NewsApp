package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.SmallNewItemBinding
import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.utils.DateUtils.formatDateRelativeToToday

class NewAdapter(
    private val callBack: (new: NewWithGenre) -> Unit,
) : ListAdapter<NewWithGenre, NewAdapter.VHMainList>(NewDiffCallback) {
    class VHMainList(val binding: SmallNewItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHMainList {
        val binding = SmallNewItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VHMainList(binding)
    }

    override fun onBindViewHolder(holder: VHMainList, position: Int) {
        val currentNewWithGenre = getItem(position)
        val imageUri = currentNewWithGenre.new.image.toUri()
        val title = currentNewWithGenre.new.name
//        val source = currentNewWithGenre.new.source
        val date = currentNewWithGenre.new.date.formatDateRelativeToToday()
        holder.binding.apply {
            Glide.with(holder.itemView.context).load(imageUri).into(ivNew)
//            ivNew.setColorFilter(
//                Color.HSVToColor(80, floatArrayOf(0f, 0f, 0f)),
//                PorterDuff.Mode.DARKEN
//            )
            tvTitle.text = title
//            tvSource.text = source
            tvDate.text = date
        }

        holder.binding.ivNew.setOnClickListener {
            callBack(currentNewWithGenre)
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