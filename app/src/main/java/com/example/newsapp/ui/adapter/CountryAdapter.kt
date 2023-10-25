package com.example.newsapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.RecyclerCountryItemBinding
import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.utils.Constants
import com.example.newsapp.utils.Country

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.VHMainList>() {
    class VHMainList(val binding: RecyclerCountryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val countries = Constants.getCountries()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHMainList {
        val binding = RecyclerCountryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VHMainList(binding)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: VHMainList, position: Int) {
        val selected = holder.binding.btnCountry.context.resources.getDrawable(
            R.drawable.selected_button,
            null
        )
        val unSelected = holder.binding.btnCountry.context.resources.getDrawable(
            R.drawable.unselected_button,
            null
        )
        val currentCountry = countries[position]

        holder.binding.btnCountry.setImageResource(currentCountry.flagImageId)
        holder.binding.btnCountry.setOnClickListener {
            currentCountry.selectThis()
            notifyDataSetChanged()
        }

        if (currentCountry.isSelected) {
            holder.binding.btnCountry.foreground = selected
            holder.binding.tvSelected.visibility = View.VISIBLE
        } else {
            holder.binding.btnCountry.foreground = unSelected
            holder.binding.tvSelected.visibility = View.INVISIBLE

        }
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    private fun Countries.selectThis() {
        countries.onEach {
            it.isSelected = false
        }
        this.isSelected = true
        Country.selectedCountry = this
    }
}