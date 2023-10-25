package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.RecyclerListItemBinding
import com.example.newsapp.domain.model.New
import com.example.newsapp.utils.Constants.Companion.formatDate

class NewAdapter(
    private val newList: List<New>,
    val callBack: (new: New) -> Unit
) : RecyclerView.Adapter<NewAdapter.VHMainList>() {
    class VHMainList(val binding: RecyclerListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHMainList {
        val binding = RecyclerListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VHMainList(binding)
    }

    override fun onBindViewHolder(holder: VHMainList, position: Int) {
        val currentNew = newList[position]
        val imageUri = currentNew.image.toUri()
        val title = currentNew.name
        val source = currentNew.source
        val date = currentNew.date.formatDate()
        holder.binding.apply {
            Glide.with(holder.itemView.context).load(imageUri).into(ivNew)
            tvTitle.text = title
            tvSource.text = source
            tvDate.text = date
        }

        holder.binding.ivNew.setOnClickListener {
            callBack(currentNew)
        }
    }

    override fun getItemCount(): Int {
        return newList.size
    }
}