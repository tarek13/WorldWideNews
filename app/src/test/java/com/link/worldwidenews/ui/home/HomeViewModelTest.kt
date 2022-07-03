package com.link.worldwidenews.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.link.worldwidenews.domain.model.news.ArticleModel

import com.link.worldwidenews.domain.repository.INewsRepository
import com.link.worldwidenews.domain.usecase.news.GetNewsUseCase
import com.link.worldwidenews.domain.util.SchedulerProvider
import com.link.worldwidenews.factory.news.ArticleFactory
import com.link.worldwidenews.model.news.Article
import com.link.worldwidenews.utils.StateListener
import com.link.worldwidenews.utils.getOrAwaitValueTest
import com.link.worldwidenews.utils.helper.ErrorHandlingUtils
import com.link.worldwidenews.utils.schedulers.TestingSchedulerProvider
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class HomeViewModelTest {
    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var newsRepository: INewsRepository

    private lateinit var stateListener: StateListener

    @Mock
    private lateinit var errorHandlingUtils: ErrorHandlingUtils



    private lateinit var getNewsUseCase: GetNewsUseCase

    private lateinit var SUT: HomeViewModel

    private lateinit var testingSchedulerProvider: SchedulerProvider

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        setUpThreadForRxJava()
        setUpUseCases()
        setUpViewModel()
    }

    private fun setUpThreadForRxJava() {
        testingSchedulerProvider = TestingSchedulerProvider()
    }

    private fun setUpUseCases() {
        getNewsUseCase = spy(GetNewsUseCase(newsRepository, testingSchedulerProvider))
        stateListener = StateListener()
    }

    private fun setUpViewModel() {
        SUT = HomeViewModel(
            getNewsUseCase,
            stateListener,
            errorHandlingUtils,
        )
    }

    @Test
    fun ` 'loadNews()' 'with correct data' 'then check that buildUseCaseSingle  called'`() {
        // Arrange
        val article = listOf(ArticleFactory.generateDummyArticleDomain())
        stubGetArticleListSuccess(Single.just(article))

        // Act
        SUT.loadNews()

        // Assert
        verify(getNewsUseCase, times(2)).buildUseCaseSingle(null)
    }

    @Test
    fun ` 'loadNews()' 'with correct data' 'then return product list item response'`() {
        // Arrange
        val articleDomain = listOf(ArticleFactory.generateDummyArticleDomain())
        val article = listOf(ArticleFactory.generateDummyArticle())
        stubGetArticleListSuccess(Single.just(articleDomain))

        // Act
        SUT.loadNews()
        val loading = stateListener.loadingProgressLiveData.getOrAwaitValueTest()
        val response =
            stateListener.successResponseLiveData.getOrAwaitValueTest() as List<Article?>?

        // Assert
        assertEquals(loading, false)
        assertEquals(response, article)
    }

    @Test
    fun ` 'loadNews()' 'with invalid url' 'then check getErrorMessage method is called'`() {
        // Arrange
        val error = RuntimeException("Wrong user name and password")
        stubGetArticleListError(error)

        // Act
        SUT.loadNews()
        val loading = stateListener.loadingProgressLiveData.getOrAwaitValueTest()

        // Assert
        assertEquals(loading, false)
        verify(errorHandlingUtils, times(1)).getErrorMessage(error, stateListener)
    }


    @Test
    fun ` 'doSearchInNewsList()' 'with query found in all list item' 'then return filtered list size as the original list size'`() {
        // Arrange
        val article = arrayListOf(ArticleFactory.generateDummyArticle())
        article.add(ArticleFactory.generateDummyArticleWithDifferentTitle())
        val query="t"

        // Act
        SUT.doSearchInNewsList(query,article)
        val response = SUT.getSearchedListLiveData.getOrAwaitValueTest()

        // Assert
        assertEquals(response?.size, article.size)
    }

    @Test
    fun ` 'doSearchInNewsList()' 'with  query not found in all list item' 'then return filtered list size is zero'`() {
        // Arrange
        val article = arrayListOf(ArticleFactory.generateDummyArticle())
        article.add(ArticleFactory.generateDummyArticleWithDifferentTitle())
        val query="o"

        // Act
        SUT.doSearchInNewsList(query,article)
        val response = SUT.getSearchedListLiveData.getOrAwaitValueTest()

        // Assert
        assertEquals(response?.size, 0)
    }

    @Test
    fun ` 'doSearchInNewsList()' 'with query equal null' 'then return filtered list size is zero'`() {
        // Arrange
        val article = arrayListOf(ArticleFactory.generateDummyArticle())
        article.add(ArticleFactory.generateDummyArticleWithDifferentTitle())
        val query=null

        // Act
        SUT.doSearchInNewsList(query,article)
        val response = SUT.getSearchedListLiveData.getOrAwaitValueTest()

        // Assert
        assertEquals(response?.size, 0)
    }

    @Test
    fun ` 'doSearchInNewsList()' 'with news list is empty' 'then return filtered list size is zero'`() {
        // Arrange
        val article = arrayListOf<Article>()
        val query="t"

        // Act
        SUT.doSearchInNewsList(query,article)
        val response = SUT.getSearchedListLiveData.getOrAwaitValueTest()

        // Assert
        assertEquals(response?.size, 0)
    }

    /**
     * Stub helpers
     */

    private fun stubGetArticleListSuccess(single: Single<List<ArticleModel?>?>) {
        `when`(getNewsUseCase.buildUseCaseSingle(null))
            .thenReturn(single)
    }

    private fun stubGetArticleListError(
        error: Throwable,
    ) {
        `when`(getNewsUseCase.buildUseCaseSingle(null))
            .thenReturn(Single.error(error))
    }


}