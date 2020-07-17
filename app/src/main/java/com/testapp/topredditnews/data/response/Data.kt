package com.testapp.topredditnews.data.response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("children")
    val children: List<Children>,
    @SerializedName("dist")
    val dist: Int
)