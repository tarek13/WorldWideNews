package com.link.worldwidenews.data.entity.news


import com.google.gson.annotations.SerializedName

data class ArticleEntity(
    @SerializedName("author")
    var author: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("publishedAt")
    var publishedAt: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("url")
    var url: String?,
    @SerializedName("urlToImage")
    var urlToImage: String?
)