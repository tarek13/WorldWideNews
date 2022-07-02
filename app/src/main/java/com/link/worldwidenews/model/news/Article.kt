package com.link.worldwidenews.model.news

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    var author: String?,
    var description: String?,
    var publishedAt: String?,
    var title: String?,
    var url: String?,
    var urlToImage: String?
) : Parcelable