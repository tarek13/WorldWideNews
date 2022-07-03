package com.link.worldwidenews.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.link.worldwidenews.model.news.Article
import com.link.worldwidenews.ui.home.HomeViewModel
import com.link.worldwidenews.utils.getOrAwaitValueTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest{
    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var SUT: MainViewModel

    @Before
    fun setUp() {
        SUT= MainViewModel()
    }

    @Test
    fun ` 'searchInNewsList()' 'with query not null' 'then return same query'`() {
        // Arrange
        val query="t"

        // Act
        SUT.searchInNewsList(query)
        val response = SUT.searchQueryLiveData.getOrAwaitValueTest()

        // Assert
        assertEquals(response, query)
    }

    @Test
    fun ` 'searchInNewsList()' 'with query  empty' 'then return same query'`() {
        // Arrange
        val query=""

        // Act
        SUT.searchInNewsList(query)
        val response = SUT.searchQueryLiveData.getOrAwaitValueTest()

        // Assert
        assertEquals(response, query)
    }
}