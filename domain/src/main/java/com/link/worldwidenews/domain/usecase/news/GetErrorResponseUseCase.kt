package com.link.worldwidenews.domain.usecase.news


import com.link.worldwidenews.domain.model.ErrorResponseModel
import com.link.worldwidenews.domain.repository.INewsRepository
import javax.inject.Inject

class GetErrorResponseUseCase @Inject constructor(private val newsRepository: INewsRepository) {

    fun getErrorResponseUseCase(errorBody: String?): ErrorResponseModel? {
        return newsRepository.handleLoginErrorResponse(errorBody)
    }
}