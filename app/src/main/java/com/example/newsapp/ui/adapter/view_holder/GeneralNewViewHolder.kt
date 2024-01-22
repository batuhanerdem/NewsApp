package com.example.newsapp.ui.adapter.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.domain.model.NewWithGenre

abstract class GeneralNewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(new: NewWithGenre)
}