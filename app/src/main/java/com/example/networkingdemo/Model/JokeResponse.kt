package com.example.networkingdemo.Model



import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable


@Parcelize
data class JokeResponse(
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: Value
) : Parcelable