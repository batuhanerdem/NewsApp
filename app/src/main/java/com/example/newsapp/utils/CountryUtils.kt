package com.example.newsapp.utils

import com.example.newsapp.domain.model.enums.Countries

object CountryUtils {
    var selectedCountry: Countries = Countries.TURKEY

    fun Countries.toSelectableData(): SelectableData<Countries> {
        val isSelected = selectedCountry == this
        return SelectableData(this, isSelected)
    }
}