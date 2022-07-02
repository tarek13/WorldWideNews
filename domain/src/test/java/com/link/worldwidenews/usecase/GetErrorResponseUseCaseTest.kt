package com.link.worldwidenews.usecase


import com.link.worldwidenews.domain.model.ErrorResponseModel
import com.link.worldwidenews.domain.repository.INewsRepository
import com.link.worldwidenews.domain.usecase.news.GetErrorResponseUseCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetErrorResponseUseCaseTest {
    private lateinit var SUT: GetErrorResponseUseCase

    @Mock
    lateinit var newsRepository: INewsRepository

    @Captor
    private lateinit var stringArgumentCaptor: ArgumentCaptor<String>

    @Before
    fun setUp() {
        SUT = GetErrorResponseUseCase(newsRepository)
    }

    @Test
    fun `'getErrorResponseUseCase()' '' 'then check  handleLoginErrorResponse method is called'`() {
        // Arrange
        val errorBody = "Not found"

        // Act
        SUT.getErrorResponseUseCase(errorBody)

        // Assert
        verify(newsRepository, times(1)).handleLoginErrorResponse(errorBody)
    }

    @Test
    fun `getErrorResponseUseCase() 'with error body' 'then check error body passed to handleLoginErrorResponse method`() {

        //arrange
        val errorBody = "Not found"


        //act
        SUT.getErrorResponseUseCase(errorBody)

        //assert
        verify(
            newsRepository,
            times(1)
        ).handleLoginErrorResponse(stringArgumentCaptor.capture())
        val captures = stringArgumentCaptor.allValues
        assertEquals(captures[0], errorBody)
    }

    @Test
    fun `getErrorResponseUseCase() 'with  error body' 'then return error response`() {
        //arrange
        val errorBody = "Not found"
        val errorResponseModel = ErrorResponseModelFactory.generateDummyErrorResponseDomain()
        stubGetErrorMessage(errorBody, errorResponseModel)

        //act
        val errorResponse = SUT.getErrorResponseUseCase(errorBody)

        //assert
        assertNotNull(errorResponse)
        assertEquals(errorResponse, errorResponseModel)
    }

    @Test
    fun `getErrorResponseUseCase() 'with null error body' 'then return error response equal null`() {

        //arrange
        val errorBody = null
        val errorResponseModel = null
        stubGetErrorMessage(errorBody, errorResponseModel)

        //act
        val errorResponse = SUT.getErrorResponseUseCase(errorBody)

        //assert
        assertNull(errorResponse)
    }

    /**
     * Stub helpers
     */

    private fun stubGetErrorMessage(
        errorBody: String?,
        errorResponseModel: ErrorResponseModel?
    ) {
        `when`(newsRepository.handleLoginErrorResponse(errorBody))
            .thenReturn(errorResponseModel)
    }
}