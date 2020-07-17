package com.testapp.topredditnews.data.response


import com.google.gson.annotations.SerializedName

data class  Children(
    @SerializedName("data")
    val `data`: DataX
)