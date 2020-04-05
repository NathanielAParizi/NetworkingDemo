package com.example.networkingdemo.Model


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import kotlinx.android.parcel.RawValue


@Parcelize
data class Value(
    @SerializedName("id")
    val id: Int,
    @SerializedName("joke")
    val joke: String
) : Parcelable