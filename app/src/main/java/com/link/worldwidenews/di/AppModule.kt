package com.link.worldwidenews.di

import android.app.Application
import com.link.worldwidenews.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    @Named("current_language")
    fun provideCurrentLanguage(context: Application): String {
        return context.getString(R.string.language)
    }
}

