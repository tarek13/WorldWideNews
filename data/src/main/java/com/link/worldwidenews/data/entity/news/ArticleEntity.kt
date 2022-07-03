package com.link.worldwidenews.data.entity.news


import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName

@Entity(tableName = "articles", primaryKeys = ["id"], indices = [Index(value = ["title"], unique = true)])
data class ArticleEntity(
    var id :Int?,
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