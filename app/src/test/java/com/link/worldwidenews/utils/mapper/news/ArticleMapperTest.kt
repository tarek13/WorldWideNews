package com.link.worldwidenews.utils.mapper.news

import com.link.worldwidenews.domain.model.news.ArticleModel
import com.link.worldwidenews.factory.news.ArticleFactory
import com.link.worldwidenews.utils.mapper.mapToView
import com.link.worldwidenews.utils.mapper.mapToViewList
import org.junit.Assert.*
import org.junit.Test

class ArticleMapperTest {
    @Test
    fun ` 'mapToView()' 'with domain model not equal null' 'return view model equal domain model'`() {
        //arrange
        val articleModel = ArticleFactory.generateDummyArticleDomain()

        //act
        val article = articleModel.mapToView()

        //assert
        assertNotNull(article)
        assertEquals(article?.author, articleModel.author)
        assertEquals(article?.description, articleModel.description)
        assertEquals(article?.publishedAt, articleModel.publishedAt)
        assertEquals(article?.title, articleModel.title)
        assertEquals(article?.url, articleModel.url)
        assertEquals(article?.urlToImage, articleModel.urlToImage)
    }

    @Test
    fun `'mapToView()' 'with domain model equal null' 'then return view model is null'`() {
        //arrange
        val articleModel: ArticleModel? = null

        //act
        val article = articleModel.mapToView()

        //assert
        assertNull(article)
    }

    @Test
    fun ` 'mapToViewList()' 'with domain model not equal null' 'return view model list equal domain model list'`() {
        //arrange
        val articleModelList =
            listOf(ArticleFactory.generateDummyArticleDomain())

        //act
        val articleList = articleModelList.mapToViewList()

        //assert
        assertNotNull(articleList)
        assertEquals(articleList[0]?.author, articleModelList[0].author)
        assertEquals(articleList[0]?.description, articleModelList[0].description)
        assertEquals(articleList[0]?.publishedAt, articleModelList[0].publishedAt)
        assertEquals(articleList[0]?.title, articleModelList[0].title)
        assertEquals(articleList[0]?.url, articleModelList[0].url)
        assertEquals(articleList[0]?.urlToImage, articleModelList[0].urlToImage)
    }

    @Test
    fun `'mapToViewList()' 'with domain model equal null' 'then return view model list is empty'`() {
        //arrange
        val articleModelList: List<ArticleModel?>? = null

        //act
        val articleList = articleModelList.mapToViewList()

        //assert
        assert(articleList.isEmpty())
    }

}