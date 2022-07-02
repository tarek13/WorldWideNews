package com.link.worldwidenews.ui.home

import androidx.lifecycle.ViewModel
import com.link.worldwidenews.domain.model.news.ArticleModel
import com.link.worldwidenews.domain.usecase.base.SingleUseCaseCallback
import com.link.worldwidenews.domain.usecase.news.GetNewsUseCase
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

    override fun onCleared() {
        super.onCleared()
        getNewsUseCase.dispose()

    }
}