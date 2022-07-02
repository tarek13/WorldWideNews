package com.link.worldwidenews.data.source.remote

import com.link.worldwidenews.data.factory.news.NewsResponseEntityFactory
import com.link.worldwidenews.data.util.TestUtils.getJson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class NewsApiTest {

    private lateinit var SUT: NewsApis

    private lateinit var mockWebServer: MockWebServer
    private lateinit var token: String

    @Before
    fun setup() {
        mockWebServer = MockWebServer()


        SUT = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(NewsApis::class.java)

        token = "token"
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }
    
    // test correct path
    @Test
    fun `getNews() 'with path' 'then success path passed to endpoint'`() {
        //arrange
        val path = "/v1/articles?source=source&apiKey=apiKey"
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("{}"))
        mockWebServer.enqueue(mockResponse)

        //act
        val testObserver = SUT.getNews("source","apiKey").test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        //assert
        val request = mockWebServer.takeRequest()
        assertEquals(request.path, path)
    }

    @Test
    fun `getNews() 'with token' 'then return news response'`() {

        //arrange
        val newsResponseEntity =
            NewsResponseEntityFactory.generateDummyNewsResponseEntity()
        // Mock a response with status 200 and sample JSON output
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("news/news-response.json"))

        // act
        mockWebServer.enqueue(mockResponse)
        val testObserver = SUT.getNews("source","apiKey").test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        // assert
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        testObserver.assertValue { newsResponse ->
            assertEquals(newsResponse, newsResponseEntity)
            true
        }
    }

    @Test
    fun `getNews() 'with wrong api url' 'then return error response'`() {

        //arrange
        val errorMessage = "Not found"
        // Mock a response with status 404 and sample JSON output
        val mockResponse = MockResponse()
            .setResponseCode(404)
            .setBody(errorMessage)

        // act
        mockWebServer.enqueue(mockResponse)
        val testObserver = SUT.getNews("source","apiKey").test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        // assert
        testObserver.assertValueCount(0)
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


}