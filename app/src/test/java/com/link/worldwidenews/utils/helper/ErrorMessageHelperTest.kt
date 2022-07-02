package com.link.worldwidenews.utils.helper

import com.link.worldwidenews.R.string
import com.link.worldwidenews.domain.util.AppConstants
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class ErrorMessageHelperTest {


    @Before
    fun setup() {

    }


    @Test
    fun ` 'showGeneralErrorMessage()' 'with  error code ERROR_CODE_NOT_CONNECTED_TO_INTERNET' 'then return not_connected_to_internet'`() {
        //Arrange
        val errorCode: Int = AppConstants.ERROR_CODE_NOT_CONNECTED_TO_INTERNET
        val errorMessage: Int = string.not_connected_to_internet

        //act
        val result = ErrorMessageHelper.showGeneralErrorMessage(errorCode)

        //assert
        assertEquals(result, errorMessage)
    }

    @Test
    fun ` 'showGeneralErrorMessage()' 'with  error code ERROR_CODE_TIME_OUT' 'then return error_msg_timeout'`() {
        //Arrange
        val errorCode: Int = AppConstants.ERROR_CODE_TIME_OUT
        val errorMessage: Int = string.error_msg_timeout

        //act
        val result = ErrorMessageHelper.showGeneralErrorMessage(errorCode)

        //assert
        assertEquals(result, errorMessage)
    }

    @Test
    fun ` 'showGeneralErrorMessage()' 'with  error code error_msg_timeout' 'then return error_load_data'`() {
        //Arrange
        val errorCode: Int = AppConstants.ERROR_CODE_LOAD_DATA
        val errorMessage: Int = string.error_load_data

        //act
        val result = ErrorMessageHelper.showGeneralErrorMessage(errorCode)

        //assert
        assertEquals(result, errorMessage)
    }
}