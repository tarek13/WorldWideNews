package com.link.worldwidenews.domain.usecase.news

import com.link.worldwidenews.domain.model.news.ArticleModel
import com.link.worldwidenews.domain.repository.INewsRepository
import com.link.worldwidenews.domain.usecase.base.SingleUseCaseWithParams
import com.link.worldwidenews.domain.util.SchedulerProvider
import io.reactivex.Single
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: INewsRepository,
    private val schedulerProvider: SchedulerProvider
) : SingleUseCaseWithParams<List<ArticleModel?>?, Unit>(schedulerProvider) {
    override fun buildUseCaseSingle(params: Unit?): Single<List<ArticleModel?>?> {
        return newsRepository.getNews()
    }
}