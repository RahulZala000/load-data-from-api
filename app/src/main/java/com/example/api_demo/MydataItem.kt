package com.example.api_demo

import com.google.gson.annotations.SerializedName

data class MydataItem(

    @SerializedName("body")
    val body: String,
    @SerializedName("id")
    val id: Int,
    val title: String,
    val userId: Int
)