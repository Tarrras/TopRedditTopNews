package com.testapp.topredditnews.data.response


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("data")
    val `data`: Data
)