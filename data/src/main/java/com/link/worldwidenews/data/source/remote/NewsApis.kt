package com.link.worldwidenews.data.source.remote

import com.link.worldwidenews.data.entity.news.NewsResponseEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApis {
    @GET("v1/articles")
    fun getNews(
        @Query("source") source: String?,
        @Query("apiKey") apiKey: String?,
    ): Single<NewsResponseEntity?>
}