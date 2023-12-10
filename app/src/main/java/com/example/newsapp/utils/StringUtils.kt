package com.example.newsapp.utils

object StringUtils {
    fun String.getFirstSentence(): String {
        return try {
            val digits = "0123456789"
            var _text = this
            var endIndex = this.indexOfAny(charArrayOf('.', '!', '?')) + 1
            while (_text[endIndex - 2] in digits && _text[endIndex - 1] == '.') {
                _text = _text.substring(endIndex, _text.length)
                endIndex = _text.indexOfAny(charArrayOf('.', '!', '?')) + 1
            }
            endIndex += this.length - _text.length
            this.substring(0, endIndex).trim()
        } catch (e: Exception) {
            println(e.message)
            this
        }

    }

}