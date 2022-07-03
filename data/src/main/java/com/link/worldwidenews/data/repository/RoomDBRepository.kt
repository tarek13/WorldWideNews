package com.link.worldwidenews.data.repository

import com.link.worldwidenews.data.source.local.room.dao.CachedNewsDao
import com.link.worldwidenews.data.source.local.shared_prefs.PreferencesHelper
import com.link.worldwidenews.data.util.mapper.news.mapToDomainList
import com.link.worldwidenews.data.util.mapper.news.mapToEntity
import com.link.worldwidenews.domain.model.news.ArticleModel
import com.link.worldwidenews.domain.repository.IRoomDBRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class RoomDBRepository @Inject constructor(
    private val cachedNewsDao: CachedNewsDao,
    private val preferencesHelper: PreferencesHelper
) : IRoomDBRepository {

    override fun saveArticles(listArticles: List<ArticleModel?>): Completable {
        return Completable.defer {
            listArticles.map { it.mapToEntity() }.forEach {
                cachedNewsDao.addArticles(it)
            }
            Completable.complete()
        }
    }

    override fun getArticles(): Single<List<ArticleModel?>> {
        return Single.defer {
            Single.just(cachedNewsDao.getArticles()).map {
                it.mapToDomainList()
            }
        }
    }

    override fun clearArticles(): Completable {
        return Completable.defer {
            cachedNewsDao.clearArticles()
            Completable.complete()
        }
    }


    override fun isCached(): Single<Boolean> {
        return Single.defer {
            Single.just(cachedNewsDao.getArticles().isNotEmpty())
        }
    }
    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

    companion object {
        /**
         * Expiration time set to 5 minutes
         */
        const val EXPIRATION_TIME = (60 * 5 * 1000).toLong()
    }
}
