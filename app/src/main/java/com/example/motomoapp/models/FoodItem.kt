package com.example.motomoapp.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class FoodItem (
    val id: String,
    val name: String,
    @SerializedName("dsc") val description: String,
    var price: String,
    @SerializedName("img") var idImage: String
)  : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(price)
        parcel.writeString(idImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FoodItem> {
        override fun createFromParcel(parcel: Parcel): FoodItem {
            return FoodItem(parcel)
        }

        override fun newArray(size: Int): Array<FoodItem?> {
            return arrayOfNulls(size)
        }
    }
}

