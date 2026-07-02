package com.elvin.purple.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: List<NewsItem>
)

data class NewsItem(
    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("link")
    val link: String
)

data class NewsImage(
    val small: String,
    val large: String
)