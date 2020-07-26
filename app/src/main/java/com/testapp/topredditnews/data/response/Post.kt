package com.testapp.topredditnews.data.response


import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("name")
    val postId: String = "",
    @SerializedName("author")
    val author: String = "",
    @SerializedName("created_utc")
    val createdUtc: Long = 0L,
    @SerializedName("num_comments")
    val numComments: Int = 0,
    @SerializedName("thumbnail")
    val thumbnail: String = "",
    @SerializedName("ups")
    val ups: Int = 0,
    @SerializedName("url_overridden_by_dest")
    val urlOverriddenByDest: String = ""
)