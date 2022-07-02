package com.link.worldwidenews.usecase.news

import com.link.worldwidenews.domain.model.news.ArticleModel
import com.link.worldwidenews.domain.repository.INewsRepository
import com.link.worldwidenews.factory.news.ArticleModelFactory
import com.link.worldwidenews.domain.usecase.news.GetNewsUseCase
import com.link.worldwidenews.domain.util.SchedulerProvider
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetNewsUseCaseTest {
    private lateinit var SUT: GetNewsUseCase

    @Mock
    lateinit var newsRepository: INewsRepository

    @Mock
    lateinit var schedulerProvider: SchedulerProvider

    @Captor
    private lateinit var stringArgumentCaptor: ArgumentCaptor<String>

    private lateinit var token: String

    @Before
    fun setUp() {
        SUT = GetNewsUseCase(newsRepository, schedulerProvider)
        token = "token"
    }

    @Test
    fun `'buildUseCaseGetProducts()' '' 'then check getNews method is called'`() {
        // Arrange
        // No Arrangement for this test case

        // Act
        SUT.buildUseCaseSingle(null)

        // Assert
        verify(newsRepository, times(1)).getNews()
    }


    @Test
    fun `'buildUseCaseGetProducts()' '' then check is completed'`() {
        // Arrange
        val articleListList = listOf(ArticleModelFactory.generateDummyArticleDomain())
        stubReturnArticleModelList(Single.just(articleListList))

        // Act
        val testObserver = SUT.buildUseCaseSingle(null).test()

        // Assert
        testObserver.assertComplete()
    }

    @Test
    fun `'buildUseCaseGetProducts()' 'with login request' 'then return login response`() {
        // Arrange
        val articleListList = listOf(ArticleModelFactory.generateDummyArticleDomain())
        stubReturnArticleModelList(Single.just(articleListList))

        // Act
        val testObserver = SUT.buildUseCaseSingle(null).test()

        // Assert
        testObserver.assertValue {
            assertEquals(it, articleListList)
            true
        }
    }

    @Test
    fun `'buildUseCaseGetProducts()' 'with wrong api url' 'then return error response'`() {

        // Arrange
        val error = RuntimeException("Not found")
        stubReturnErrorResponse(error)

        // Act
        val testObserver = SUT.buildUseCaseSingle(null).test()

        // Assert
        testObserver.assertNotComplete()
        testObserver.assertError {
            it is RuntimeException
        }
    }

    /**
     * Stub helpers
     */

    private fun stubReturnArticleModelList(
        single: Single<List<ArticleModel?>?>,
    ) {
        `when`(newsRepository.getNews())
            .thenReturn(single)
    }

    private fun stubReturnErrorResponse(
        error: Throwable,
    ) {
        `when`(newsRepository.getNews())
            .thenReturn(Single.error(error))
    }
}