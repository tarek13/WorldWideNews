package com.link.worldwidenews.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.link.worldwidenews.data.entity.news.ArticleEntity
import com.link.worldwidenews.data.source.local.room.dao.CachedNewsDao

@Database(
    entities = [ArticleEntity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(
    NewsDatabase.Converters::class
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun cachedNewsDao(): CachedNewsDao
    class Converters {
        @TypeConverter
        fun restoreList(listOfString: String?): List<String?>? {
            return Gson().fromJson(listOfString, object : TypeToken<List<String?>?>() {}.getType())
        }

        @TypeConverter
        fun saveList(listOfString: List<String?>?): String? {
            return Gson().toJson(listOfString)
        }
    }
}


