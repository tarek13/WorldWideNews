package com.link.worldwidenews.data.util.mapper

import com.link.worldwidenews.data.entity.ErrorResponseEntity
import com.link.worldwidenews.data.factory.ErrorResponseEntityFactory
import com.link.worldwidenews.data.util.mapper.error.mapToDomain
import org.junit.Assert.*
import org.junit.Test

class ErrorResponseEntityMapperTest {
    @Test
    fun ` 'mapToDomain()' 'with data entity not equal null' 'return data entity equal domain model'`() {
        //arrange
        val errorResponseEntity = ErrorResponseEntityFactory.generateDummyErrorResponseEntity()

        //act
        val errorResponseModel = errorResponseEntity.mapToDomain()

        //assert
        assertNotNull(errorResponseModel)
        assertEquals(errorResponseModel?.code, errorResponseEntity.code)
        assertEquals(errorResponseModel?.status, errorResponseEntity.status)
        assertEquals(errorResponseModel?.message, errorResponseEntity.message)
    }

    @Test
    fun `'mapToDomain()' 'with data entity equal null' 'then return domain model is null'`() {
        //arrange
        val errorResponseEntity: ErrorResponseEntity? = null

        //act
        val errorResponseModel = errorResponseEntity.mapToDomain()

        //assert
        assertNull(errorResponseModel)
    }
}