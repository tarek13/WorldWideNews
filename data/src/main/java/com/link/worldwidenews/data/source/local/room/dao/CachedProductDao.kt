package com.link.worldwidenews.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.link.worldwidenews.data.entity.news.ArticleEntity

@Dao
interface CachedNewsDao {

    @Query("SELECT * FROM articles")
    fun getArticles(): List<ArticleEntity?>


    @Query("DELETE FROM articles")
    fun clearArticles()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArticles(articleEntity: ArticleEntity?)
}
