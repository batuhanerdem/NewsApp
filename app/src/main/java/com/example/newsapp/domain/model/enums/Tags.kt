package com.example.newsapp.domain.model.enums

import android.os.Parcel
import android.os.Parcelable

enum class Tags(val value: String, val title: String) : Parcelable {
    GENERAL("general", "General"),
    SPORT("sport", "Sport"),
    ECONOMY("economy", "Economy"),
    TECHNOLOGY("technology", "Technology");

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(value)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tags> {
        override fun createFromParcel(parcel: Parcel): Tags {
            return entries.toList()[parcel.readInt()]
        }

        override fun newArray(size: Int): Array<Tags?> {
            return arrayOfNulls(size)
        }
    }


}