package com.link.worldwidenews.data.repository

import com.link.worldwidenews.data.entity.ErrorResponseEntity
import com.link.worldwidenews.data.entity.news.NewsResponseEntity
import com.link.worldwidenews.data.factory.ErrorResponseEntityFactory
import com.link.worldwidenews.data.factory.news.ArticleEntityFactory
import com.link.worldwidenews.data.factory.news.NewsResponseEntityFactory
import com.link.worldwidenews.data.source.remote.NewsApis
import com.link.worldwidenews.data.util.HandleApiErrors
import com.link.worldwidenews.data.util.TestUtils.getJson
import com.link.worldwidenews.domain.model.ErrorResponseModel
import com.link.worldwidenews.domain.repository.IRoomDBRepository
import com.link.worldwidenews.domain.util.AppConstants
import io.reactivex.Single
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class NewsRepositoryTest {
    private lateinit var SUT: NewsRepository

    @Mock
    private lateinit var newsApis: NewsApis
    
    @Mock
    private lateinit var handleApiErrors: HandleApiErrors

    @Mock
    private lateinit var dbRepository: RoomDBRepository

    @Captor
    private lateinit var stringArgumentCaptor: ArgumentCaptor<String>


    private val associatedPressSource:String="associated-press"
    private val theNextWebSource:String="the-next-web"

    @Before
    fun setup() {
        SUT = NewsRepository(newsApis, "lang",handleApiErrors,dbRepository)
    }

    @Test
    fun `'getNewsRemotely()' 'with token' 'then check token passed to endpoint'`() {

        //arrange
        val newsResponseEntity =
            NewsResponseEntityFactory.generateDummyNewsResponseEntity()
        stubReturnNewsResponse(
            Single.just(newsResponseEntity),
        )

        //act
        SUT.getNewsRemotely()

        //assert
        verify(newsApis, times(2)).getNews(stringArgumentCaptor.capture(), (stringArgumentCaptor.capture()))
            val captures = stringArgumentCaptor.allValues
        assertEquals(captures[0], associatedPressSource)
        assertEquals(captures[1], AppConstants.API_KEY)
        assertEquals(captures[2], theNextWebSource)
        assertEquals(captures[3], AppConstants.API_KEY)
    }

    @Test
    fun `'getNewsRemotely()' 'with source and api key' 'then return news response'`() {

        // Arrange
        val newsResponseEntity =
            NewsResponseEntityFactory.generateDummyNewsResponseEntity()
        val articleDomainList =
            listOf(ArticleEntityFactory.generateDummyArticleDomain(),ArticleEntityFactory.generateDummyArticleDomain())
        stubReturnNewsResponse(Single.just(newsResponseEntity))


        // Act
        val testObserver = SUT.getNewsRemotely().test()

        // Assert
        verify(newsApis, times(2)).getNews(anyString(), anyString())
        testObserver.assertValue {
            assertEquals(it, articleDomainList)
            true
        }
    }

    @Test
    fun `'getNewsRemotely()' 'with wrong api url' 'then return error response'`() {

        // Arrange
        val errorMessage = "Not found"
        val error = HttpException(
            Response.error<ResponseBody>(
                404,
                errorMessage.toResponseBody()
            )
        )
        stubApiNotFoundError(error)


        // Act
        val testObserver = SUT.getNewsRemotely().test()

        // Assert
        testObserver.assertError {
            if (it is HttpException) {
                assertEquals(it.response()?.errorBody()?.string(), errorMessage)
                assertEquals(it.code(), 404)
                true
            } else {
                false
            }
        }
    }

    @Test
    fun `handleLoginErrorResponse() 'with error body' 'then check error body passed to handleErrorResponse method `() {
        //arrange
        val errorBody = getJson("error-response.json")
        val errorResponseEntity = ErrorResponseEntityFactory.generateDummyErrorResponseEntity()
        stubReturnErrorResponse(errorResponseEntity, errorBody)


        //act
        SUT.handleLoginErrorResponse(errorBody)

        //assert
        verify(handleApiErrors, times(1)).handleErrorResponse(stringArgumentCaptor.capture())
        val captures = stringArgumentCaptor.allValues
        assertEquals(captures[0], errorBody)
    }

    @Test
    fun `handleLoginErrorResponse() 'with error body' 'then return error response entity `() {
        //arrange
        val errorBody = getJson("error-response.json")
        val errorResponseEntity = ErrorResponseEntityFactory.generateDummyErrorResponseEntity()
        val errorResponseDomain = ErrorResponseEntityFactory.generateDummyErrorResponseDomain()
        stubReturnErrorResponse(errorResponseEntity, errorBody)


        //act
        val errorResponse = SUT.handleLoginErrorResponse(errorBody)

        //assert
        verify(handleApiErrors, times(1)).handleErrorResponse(errorBody)

        Assert.assertNotNull(errorResponse)
        assertEquals(errorResponse, errorResponseDomain)
    }

    @Test
    fun `handleLoginErrorResponse() 'with invalid json format' 'then return null `() {

        //arrange
        val errorBody = "dummy error body"
        stubReturnErrorResponse(null, errorBody)

        //act
        val errorResponse = SUT.handleLoginErrorResponse(errorBody)

        //assert
        Assert.assertNull(errorResponse)
    }


    @Test
    fun `handleLoginErrorResponse() 'with wrong json error body' 'then return error response field equal null `() {

        //arrange
        val errorResponseEntity = ErrorResponseEntity()
        val errorResponseModel = ErrorResponseModel()
        val errorBody: String = getJson("auth/login-response.json")
        stubReturnErrorResponse(errorResponseEntity, errorBody)

        //act
        val errorResponse = SUT.handleLoginErrorResponse(errorBody)

        //assert
        Assert.assertNotNull(errorResponse)
        assertEquals(errorResponse, errorResponseModel)
    }



    /**
     * Stub helpers
     */

    private fun stubReturnNewsResponse(
        single: Single<NewsResponseEntity?>,
    ) {
        `when`(newsApis.getNews(associatedPressSource, AppConstants.API_KEY))
            .thenReturn(single)
        `when`(newsApis.getNews(theNextWebSource,AppConstants.API_KEY))
            .thenReturn(single)

    }

    private fun stubApiNotFoundError(
        error: HttpException,
    ) {
        `when`(newsApis.getNews(associatedPressSource, AppConstants.API_KEY))
            .thenReturn(Single.error(error))
        `when`(newsApis.getNews(theNextWebSource,AppConstants.API_KEY))
            .thenReturn(Single.error(error))
    }

    private fun stubReturnErrorResponse(
        errorResponseEntity: ErrorResponseEntity?,
        errorBody: String
    ) {
        `when`(handleApiErrors.handleErrorResponse(errorBody))
            .thenReturn(errorResponseEntity)
    }

}