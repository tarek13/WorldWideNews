package com.link.worldwidenews.utils.mapper

import com.link.worldwidenews.domain.model.ErrorResponseModel
import com.link.worldwidenews.factory.ErrorResponseFactory
import org.junit.Assert.*
import org.junit.Test

class ErrorResponseMapperTest {
    @Test
    fun ` 'mapToView()' 'with domain model not equal null' 'return view model equal domain model'`() {
        //arrange
        val errorResponseModel = ErrorResponseFactory.generateDummyErrorResponseDomain()

        //act
        val errorResponse = errorResponseModel.mapToView()

        //assert
        assertNotNull(errorResponse)
        assertEquals(errorResponse?.code, errorResponseModel.code)
        assertEquals(errorResponse?.status, errorResponseModel.status)
        assertEquals(errorResponse?.message, errorResponseModel.message)
    }

    @Test
    fun `'mapToView()' 'with domain model equal null' 'then return view model is null'`() {
        //arrange
        val errorResponseModel: ErrorResponseModel? = null

        //act
        val errorResponse = errorResponseModel.mapToView()

        //assert
        assertNull(errorResponse)
    }
}