package com.link.worldwidenews.domain.repository

import com.link.worldwidenews.domain.model.ErrorResponseModel
import com.link.worldwidenews.domain.model.news.ArticleModel
import io.reactivex.Single

interface INewsRepository {

    fun getNews(): Single<List<ArticleModel?>?>
    fun handleLoginErrorResponse(errorBody: String?): ErrorResponseModel?
}