package com.link.worldwidenews.data.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.link.worldwidenews.data.source.local.room.NewsDatabase
import com.link.worldwidenews.data.source.local.room.dao.CachedNewsDao
import com.link.worldwidenews.data.source.local.shared_prefs.AppPreferencesHelper
import com.link.worldwidenews.data.source.local.shared_prefs.PreferencesHelper
import com.link.worldwidenews.domain.util.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class LocalDataModule {
    @Provides
    @Named("pref_name")
    fun providerSharedPrefsName(): String {
        return AppConstants.PREF_NAME
    }

    @Provides
    @Singleton
    fun providerAppPrefs(
        @ApplicationContext context: Context,
        gson: Gson,
        @Named("pref_name") name: String
    ): PreferencesHelper {
        return AppPreferencesHelper(context, name, gson)
    }

    @Provides
    @Named("db_name")
    fun providerAppDatabaseName(): String {
        return AppConstants.DB_NAME
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        @Named("db_name") name: String
    ): NewsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java, name
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideVoucherProductsDao(appDatabase: NewsDatabase): CachedNewsDao {
        return appDatabase.cachedNewsDao()
    }
}