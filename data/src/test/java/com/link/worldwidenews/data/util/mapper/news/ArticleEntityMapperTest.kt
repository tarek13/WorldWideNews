package com.link.worldwidenews.data.util.mapper.news

import com.link.worldwidenews.data.entity.news.ArticleEntity
import com.link.worldwidenews.data.factory.news.ArticleEntityFactory
import org.junit.Assert.*
import org.junit.Test

class ArticleEntityMapperTest {
    @Test
    fun ` 'mapToDomain()' 'with data entity not equal null' 'return data entity equal domain model'`() {
        //arrange
        val articleEntity = ArticleEntityFactory.generateDummyArticleEntity()

        //act
        val articleModel = articleEntity.mapToDomain()

        //assert
        assertNotNull(articleModel)
        assertEquals(articleModel?.author, articleEntity.author)
        assertEquals(articleModel?.title, articleEntity.title)
        assertEquals(articleModel?.description, articleEntity.description)
        assertEquals(articleModel?.url, articleEntity.url)
        assertEquals(articleModel?.urlToImage, articleEntity.urlToImage)
        assertEquals(articleModel?.publishedAt, articleEntity.publishedAt)
    }

    @Test
    fun `'mapToDomain()' 'with data entity equal null' 'then return domain model is null'`() {
        //arrange
        val articleEntity: ArticleEntity? = null

        //act
        val articleModel = articleEntity.mapToDomain()

        //assert
        assertNull(articleModel)
    }

    @Test
    fun ` 'mapToDomainList()' 'with entity not equal null' 'return data entity equal domain model'`() {
        //arrange
        val articleEntityList =
            listOf(ArticleEntityFactory.generateDummyArticleEntity())

        //act
        val articleModelList = articleEntityList.mapToDomainList()

        //assert
        assertNotNull(articleModelList)
        assertEquals(articleModelList[0]?.author, articleEntityList[0].author)
        assertEquals(articleModelList[0]?.title, articleEntityList[0].title)
        assertEquals(
            articleModelList[0]?.description,
            articleEntityList[0].description
        )
        assertEquals(articleModelList[0]?.url, articleEntityList[0].url)
        assertEquals(articleModelList[0]?.urlToImage, articleEntityList[0].urlToImage)
        assertEquals(articleModelList[0]?.publishedAt, articleEntityList[0].publishedAt)
    }

    @Test
    fun `'mapToDomainList()' 'with data entity equal null' 'then return data model list is empty'`() {
        //arrange
        val articleEntityList: List<ArticleEntity?>? = null

        //act
        val articleModelList = articleEntityList.mapToDomainList()

        //assert
        assert(articleModelList.isEmpty())
    }

}