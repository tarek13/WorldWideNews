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
        val productListItem = listOf(ArticleFactory.generateDummyArticleDomain())
        stubGetProductListSuccess(Single.just(productListItem))

        // Act
        SUT.loadNews()

        // Assert
        verify(getNewsUseCase, times(2)).buildUseCaseSingle(null)
    }

    @Test
    fun ` 'loadNews()' 'with correct data' 'then return product list item response'`() {
        // Arrange
        val productListItemDomain = listOf(ArticleFactory.generateDummyArticleDomain())
        val productListItem = listOf(ArticleFactory.generateDummyArticle())
        stubGetProductListSuccess(Single.just(productListItemDomain))

        // Act
        SUT.loadNews()
        val loading = stateListener.loadingProgressLiveData.getOrAwaitValueTest()
        val response =
            stateListener.successResponseLiveData.getOrAwaitValueTest() as List<Article?>?

        // Assert
        assertEquals(loading, false)
        assertEquals(response, productListItem)
    }

    @Test
    fun ` 'loadNews()' 'with invalid url' 'then check getErrorMessage method is called'`() {
        // Arrange
        val error = RuntimeException("Wrong user name and password")
        stubGetProductListError(error)

        // Act
        SUT.loadNews()
        val loading = stateListener.loadingProgressLiveData.getOrAwaitValueTest()

        // Assert
        assertEquals(loading, false)
        verify(errorHandlingUtils, times(1)).getErrorMessage(error, stateListener)
    }

    /**
     * Stub helpers
     */

    private fun stubGetProductListSuccess(single: Single<List<ArticleModel?>?>) {
        `when`(getNewsUseCase.buildUseCaseSingle(null))
            .thenReturn(single)
    }

    private fun stubGetProductListError(
        error: Throwable,
    ) {
        `when`(getNewsUseCase.buildUseCaseSingle(null))
            .thenReturn(Single.error(error))
    }


}