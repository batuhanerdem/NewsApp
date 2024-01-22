package com.example.newsapp.domain.model.enums

import android.os.Parcel
import android.os.Parcelable
import com.example.newsapp.R

enum class Countries(val id: Int, val value: String, val flagImageId: Int) : Parcelable {
    TURKEY(0, "tr", R.drawable.country_turkey),
    DEUTSCHLAND(1, "de", R.drawable.country_deutschland),
    ENGLAND(2, "en", R.drawable.country_england),
    RUSSIA(3, "ru", R.drawable.country_russia);

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString()!!, parcel.readInt())


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(value)
        parcel.writeInt(flagImageId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Countries> {
        override fun createFromParcel(parcel: Parcel): Countries {
            return entries[parcel.readInt()]
        }

        override fun newArray(size: Int): Array<Countries?> {
            return arrayOfNulls(size)
        }
    }
}