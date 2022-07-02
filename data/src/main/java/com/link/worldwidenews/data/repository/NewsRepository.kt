package com.link.worldwidenews.data.repository

import com.link.worldwidenews.data.source.remote.NewsApis
import com.link.worldwidenews.data.util.HandleApiErrors
import com.link.worldwidenews.data.util.mapper.error.mapToDomain
import com.link.worldwidenews.data.util.mapper.news.mapToDomainList
import com.link.worldwidenews.domain.model.ErrorResponseModel
import com.link.worldwidenews.domain.model.news.ArticleModel
import com.link.worldwidenews.domain.repository.INewsRepository
import com.link.worldwidenews.domain.util.AppConstants
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class NewsRepository @Inject constructor(
    private val newsApis: NewsApis,
    @param:Named("current_language")
    private val currentLanguage: String,
    private val handleApiErrors: HandleApiErrors
) : INewsRepository {

    override fun getNews(): Single<List<ArticleModel?>?> {
        return newsApis.getNews("associated-press", AppConstants.API_KEY)
            .map { newsResponse ->
                newsResponse.articles.mapToDomainList()
            }
    }

    override fun handleLoginErrorResponse(errorBody: String?): ErrorResponseModel? {
        return handleApiErrors.handleErrorResponse(errorBody).mapToDomain()
    }
}