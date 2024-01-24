package com.example.newsapp.utils

object StringUtils {
    fun String.getFirstSentence(): String {
        return try {
            val digits = "0123456789"
            var text = this
            var endIndex = this.indexOfAny(charArrayOf('.', '!', '?')) + 1
            while (text[endIndex - 2] in digits && text[endIndex - 1] == '.') {
                text = text.substring(endIndex, text.length)
                endIndex = text.indexOfAny(charArrayOf('.', '!', '?')) + 1
            }
            endIndex += this.length - text.length
            this.substring(0, endIndex).trim()
        } catch (e: Exception) {
            println(e.message)
            this
        }

    }

}