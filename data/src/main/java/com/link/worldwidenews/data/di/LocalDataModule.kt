package com.link.worldwidenews.data.di

import com.link.worldwidenews.domain.util.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@InstallIn(SingletonComponent::class)
@Module
class LocalDataModule {
    @Provides
    @Named("pref_name")
    fun providerSharedPrefsName(): String {
        return AppConstants.PREF_NAME
    }

    /*@Provides
    @Singleton
    fun providerAppPrefs(
        @ApplicationContext context: Context,
        gson: Gson,
        @Named("pref_name") name: String
    ): PreferencesHelper {
        return AppPreferencesHelper(context, name, gson)
    }*/
}