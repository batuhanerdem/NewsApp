package com.example.newsapp.utils

data class SelectableData<T>(val data: T, var isSelected: Boolean) {
    fun getReversed(): SelectableData<T> {
        return SelectableData(this.data, !this.isSelected)
    }
}