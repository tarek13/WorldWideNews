package com.link.worldwidenews.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.link.worldwidenews.domain.model.news.ArticleModel
import com.link.worldwidenews.domain.usecase.base.SingleUseCaseCallback
import com.link.worldwidenews.domain.usecase.news.GetNewsUseCase
import com.link.worldwidenews.model.news.Article
import com.link.worldwidenews.utils.SingleLiveEvent
import com.link.worldwidenews.utils.StateListener
import com.link.worldwidenews.utils.helper.ErrorHandlingUtils
import com.link.worldwidenews.utils.mapper.mapToViewList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
     val stateListener: StateListener,
    private val errorHandlingUtils: ErrorHandlingUtils,
) : ViewModel() {
    private val _searchedListSingleLiveEvent = SingleLiveEvent<List<Article?>?>()
    val getSearchedListLiveData: LiveData<List<Article?>?> = _searchedListSingleLiveEvent
    fun loadNews() {
        stateListener.setLoadingProgressLiveData(true)
        getNewsUseCase.execute(
            singleUseCaseCallback = object : SingleUseCaseCallback<List<ArticleModel?>?> {
                override fun onSuccess(response: List<ArticleModel?>?) {
                    stateListener.setLoadingProgressLiveData(false)
                    stateListener.setSuccessResponse(response.mapToViewList())
                }

                override fun onError(throwable: Throwable) {
                    stateListener.setLoadingProgressLiveData(false)
                    errorHandlingUtils.getErrorMessage(throwable, stateListener)
                }

            }
        )
    }
    fun doSearchInNewsList(query: String?, newsList: List<Article?>) {
      val result=  newsList.filter {article->
            (article?.title?.lowercase()?.contains(query?.lowercase().toString())==true)
        }
        _searchedListSingleLiveEvent.value=result
    }

    override fun onCleared() {
        super.onCleared()
        getNewsUseCase.dispose()

    }


}