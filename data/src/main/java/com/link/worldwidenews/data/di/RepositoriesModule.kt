package com.link.worldwidenews.data.di

import com.link.worldwidenews.data.repository.NewsRepository
import com.link.worldwidenews.domain.repository.INewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideNewRepository(newsRepository: NewsRepository): INewsRepository {
        return newsRepository
    }
}