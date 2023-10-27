package com.example.newsapp.domain.model

import android.os.Parcel
import android.os.Parcelable

data class New(
    val key: String,
    val name: String,
    val url: String,
    val description: String,
    val image: String,
    val source: String,
    val date: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeString(source)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<New> {
        override fun createFromParcel(parcel: Parcel): New {
            return New(parcel)
        }

        override fun newArray(size: Int): Array<New?> {
            return arrayOfNulls(size)
        }
    }
}