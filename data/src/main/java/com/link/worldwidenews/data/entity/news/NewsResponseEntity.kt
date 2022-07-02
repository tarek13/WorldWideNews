package com.link.worldwidenews.data.entity.news


import com.google.gson.annotations.SerializedName

data class NewsResponseEntity(
    @SerializedName("articles")
    var articles: List<ArticleEntity?>?,
    @SerializedName("sortBy")
    var sortBy: String?,
    @SerializedName("source")
    var source: String?,
    @SerializedName("status")
    var status: String?
)