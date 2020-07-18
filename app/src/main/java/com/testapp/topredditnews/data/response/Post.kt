package com.testapp.topredditnews.data.response


import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("author")
    val author: String,
    @SerializedName("created_utc")
    val createdUtc: Double,
    @SerializedName("num_comments")
    val numComments: Int,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("ups")
    val ups: Int,
    @SerializedName("url_overridden_by_dest")
    val urlOverriddenByDest: String
)