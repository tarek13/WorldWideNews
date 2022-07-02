package com.link.worldwidenews.data.util

import com.link.worldwidenews.data.factory.ErrorResponseEntityFactory
import com.google.gson.Gson
import com.link.worldwidenews.data.entity.ErrorResponseEntity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class HandleApiErrorsTest {
    private lateinit var SUT: HandleApiErrors

    @Before
    fun setup() {
        SUT = HandleApiErrors(Gson())
    }

    @Test
    fun `handleApiError() 'with null error body' 'then return error equal null`() {

        //arrange
        val errorBody: String? = null


        //act
        val errorMessage = SUT.handleErrorResponse(errorBody)

        //assert
        assertNull(errorMessage)
    }

    @Test
    fun `handleApiError() 'with error body' 'then return error `() {

        //arrange
        val errorResponseEntity = ErrorResponseEntityFactory.generateDummyErrorResponseEntity()


        //act
        val errorBody: String = TestUtils.getJson("error-response.json")
        val errorMessage = SUT.handleErrorResponse(errorBody)

        //assert
        assertNotNull(errorMessage)
        assertEquals(errorMessage, errorResponseEntity)
    }

    @Test
    fun `handleApiError() 'with invalid json format' 'then return null `() {

        //arrange
        val errorBody = "dummy error body"

        //act

        val errorMessage = SUT.handleErrorResponse(errorBody)

        //assert
        assertNull(errorMessage)
    }

    @Test
    fun `handleApiError() 'with wrong json error body' 'then return error response empty `() {

        //arrange
        val errorResponseEntity = null
        val errorBody: String = TestUtils.getJson("auth/login-response.json")

        //act
        val errorMessage = SUT.handleErrorResponse(errorBody)

        //assert
        assertEquals(errorMessage, errorResponseEntity)
    }
}