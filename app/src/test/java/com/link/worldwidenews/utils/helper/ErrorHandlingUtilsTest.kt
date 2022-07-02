package com.link.worldwidenews.utils.helper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.link.worldwidenews.domain.model.ErrorResponseModel
import com.link.worldwidenews.domain.usecase.news.GetErrorResponseUseCase
import com.link.worldwidenews.domain.util.AppConstants

import com.link.worldwidenews.factory.ErrorResponseFactory
import com.link.worldwidenews.utils.NetworkUtils
import com.link.worldwidenews.utils.StateListener

import com.link.worldwidenews.utils.TestUtils.getJson
import com.link.worldwidenews.utils.getOrAwaitValueTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.TimeoutException

@RunWith(MockitoJUnitRunner::class)
class ErrorHandlingUtilsTest {
    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var SUT: ErrorHandlingUtils

    @Mock
    lateinit var networkUtils: NetworkUtils

    @Mock
    lateinit var getErrorResponseUseCase: GetErrorResponseUseCase

    lateinit var stateListener: StateListener

    @Before
    fun setup() {
        stateListener = StateListener()
        SUT = ErrorHandlingUtils(networkUtils, getErrorResponseUseCase)
    }


    @Test
    fun ` 'getErrorMessage()' 'with  no internet connection' 'then return no internet connection error'`() {
        //Arrange
        stubCheckNetworkConnection(false)

        //act
        SUT.getErrorMessage(RuntimeException("No Internet"), stateListener)
        val result = stateListener.errorMessageLiveData.getOrAwaitValueTest() as Int

        //assert
        assertEquals(result, AppConstants.ERROR_CODE_NOT_CONNECTED_TO_INTERNET)
    }

    @Test
    fun ` 'getErrorMessage()' 'with  TimeoutException' 'then return time out error'`() {
        //Arrange
        stubCheckNetworkConnection(true)

        //act
        SUT.getErrorMessage(TimeoutException("No Internet"), stateListener)
        val result = stateListener.errorMessageLiveData.getOrAwaitValueTest() as Int

        //assert
        assertEquals(result, AppConstants.ERROR_CODE_TIME_OUT)
    }

    @Test
    fun ` 'getErrorMessage()' 'with  HttpException 404' 'then return error load data'`() {
        //Arrange
        stubCheckNetworkConnection(true)
        val errorMessage = "Not found"
        val error = HttpException(
            Response.error<ResponseBody>(
                404,
                errorMessage.toResponseBody()
            )
        )

        //act
        SUT.getErrorMessage(error, stateListener)
        val result = stateListener.errorMessageLiveData.getOrAwaitValueTest() as Int

        //assert
        assertEquals(result, AppConstants.ERROR_CODE_LOAD_DATA)
    }

    @Test
    fun ` 'getErrorMessage()' 'with  HttpException 500' 'then return error load data'`() {
        //Arrange
        stubCheckNetworkConnection(true)
        val errorMessage = "Not found"
        val error = HttpException(
            Response.error<ResponseBody>(
                500,
                errorMessage.toResponseBody()
            )
        )

        //act
        SUT.getErrorMessage(error, stateListener)
        val result = stateListener.errorMessageLiveData.getOrAwaitValueTest() as Int

        //assert
        assertEquals(result, AppConstants.ERROR_CODE_LOAD_DATA)
    }

    @Test
    fun ` 'getErrorMessage()' 'with  HttpException 401' 'then return unauthorized error'`() {
        //Arrange
        stubCheckNetworkConnection(true)
        val errorMessage = "Not found"
        val error = HttpException(
            Response.error<ResponseBody>(
                401,
                errorMessage.toResponseBody()
            )
        )

        //act
        SUT.getErrorMessage(error, stateListener)
        val result = stateListener.unAuthorizedErrorLiveData.getOrAwaitValueTest()

        //assert
        assertEquals(result, true)
    }

    @Test
    fun ` 'getErrorMessage()' 'with  HttpException 400' 'then check getErrorResponseUseCase method is called'`() {
        //Arrange
        stubCheckNetworkConnection(true)
        val errorMessage = "Not found"
        val error = HttpException(
            Response.error<ResponseBody>(
                400,
                errorMessage.toResponseBody()
            )
        )
        //act
        SUT.getErrorMessage(error, stateListener)

        //assert
        verify(getErrorResponseUseCase, times(1)).getErrorResponseUseCase(errorMessage)
    }

    @Test
    fun ` 'getErrorMessage()' 'with  HttpException 400' 'then return errorResponse model'`() {
        //Arrange
        stubCheckNetworkConnection(true)
        val errorResponseModel = ErrorResponseFactory.generateDummyErrorResponseDomain()
        val errorResponse = ErrorResponseFactory.generateDummyErrorResponse()
        val errorMessage = getJson("error-response.json")
        val error = HttpException(
            Response.error<ResponseBody>(
                400,
                errorMessage.toResponseBody("application/json".toMediaTypeOrNull())
            )
        )
        stubGetErrorResponse(errorMessage, errorResponseModel)

        //act
        SUT.getErrorMessage(error, stateListener)
        val result = stateListener.errorMessageLiveData.getOrAwaitValueTest() as String

        //assert
        assertEquals(result, errorResponse.message)

    }

    @Test
    fun ` 'getErrorMessage()' 'with  HttpException 405' 'then return error load data'`() {
        //Arrange
        stubCheckNetworkConnection(true)
        val errorMessage = "Not found"
        val error = HttpException(
            Response.error<ResponseBody>(
                405,
                errorMessage.toResponseBody()
            )
        )
        //act
        SUT.getErrorMessage(error, stateListener)
        val result = stateListener.errorMessageLiveData.getOrAwaitValueTest() as Int

        //assert
        assertEquals(result, AppConstants.ERROR_CODE_LOAD_DATA)
    }

    @Test
    fun ` 'getErrorMessage()' 'with  NullPointerException' 'then return error load data'`() {
        //Arrange
        stubCheckNetworkConnection(true)

        //act
        SUT.getErrorMessage(NullPointerException(), stateListener)
        val result = stateListener.errorMessageLiveData.getOrAwaitValueTest() as Int

        //assert
        assertEquals(result, AppConstants.ERROR_CODE_LOAD_DATA)
    }


    /**
     * Stub helpers
     */

    private fun stubCheckNetworkConnection(isConnected: Boolean) {
        `when`(networkUtils.checkForInternetConnected()).thenReturn(isConnected)
    }

    private fun stubGetErrorResponse(errorBody: String, errorResponseModel: ErrorResponseModel) {
        `when`(getErrorResponseUseCase.getErrorResponseUseCase(errorBody)).thenReturn(
            errorResponseModel
        )
    }
}