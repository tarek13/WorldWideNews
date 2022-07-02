package com.link.worldwidenews.utils.helper


import com.link.worldwidenews.domain.usecase.news.GetErrorResponseUseCase
import com.link.worldwidenews.domain.util.AppConstants
import com.link.worldwidenews.utils.NetworkUtils
import com.link.worldwidenews.utils.StateListener
import com.link.worldwidenews.utils.mapper.mapToView
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandlingUtils @Inject constructor(
    private val networkUtils: NetworkUtils,
    private val getErrorResponseUseCase: GetErrorResponseUseCase,
) {

    fun getErrorMessage(
        throwable: Throwable,
        stateListener: StateListener,
    ) {
        if (!networkUtils.checkForInternetConnected()) {
            stateListener.setErrorMessageLiveData(AppConstants.ERROR_CODE_NOT_CONNECTED_TO_INTERNET)
        } else if (throwable is TimeoutException) {
            stateListener.setErrorMessageLiveData(AppConstants.ERROR_CODE_TIME_OUT)
        } else if (throwable is HttpException) {
            getHttpErrorMessage(throwable, stateListener)
        } else {
            stateListener.setErrorMessageLiveData(AppConstants.ERROR_CODE_LOAD_DATA)
        }
    }

    fun getHttpErrorMessage(throwable: HttpException, stateListener: StateListener) {
        if (throwable.response()?.code() == 400 ||
            throwable.response()?.code() == 404 ||
            throwable.response()?.code() == 422
        ) {
            var errorBody: String? = null
            try {
                errorBody = throwable.response()
                    ?.errorBody()?.string()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val errorResponse =
                getErrorResponseUseCase.getErrorResponseUseCase(errorBody).mapToView()
            if (errorResponse?.message != null && errorResponse.message?.isNotEmpty() == true) {
                stateListener.setErrorMessageLiveData(errorResponse.message)
            } else {
                stateListener.setErrorMessageLiveData(AppConstants.ERROR_CODE_LOAD_DATA)
            }
        } else if (throwable.response()?.code() == 500) {
            stateListener.setErrorMessageLiveData(AppConstants.ERROR_CODE_LOAD_DATA)
        } else if (throwable.response()?.code() == 401) {
            stateListener.setUnAuthorizedErrorLiveData(true)
        } else {
            stateListener.setErrorMessageLiveData(AppConstants.ERROR_CODE_LOAD_DATA)
        }
    }
}
