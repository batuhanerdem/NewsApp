package com.example.newsapp.domain.model

import android.os.Parcel
import android.os.Parcelable

data class NewWithGenre(val new: New, val genre: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        New.createFromParcel(parcel),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(genre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewWithGenre> {
        override fun createFromParcel(parcel: Parcel): NewWithGenre {
            return NewWithGenre(parcel)
        }

        override fun newArray(size: Int): Array<NewWithGenre?> {
            return arrayOfNulls(size)
        }
    }

}
