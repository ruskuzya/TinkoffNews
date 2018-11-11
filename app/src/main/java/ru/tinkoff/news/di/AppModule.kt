package ru.tinkoff.news.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    @ActivityScope
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    @ActivityScope
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    internal fun provideSharedPreferences(): SharedPreferences {
        return application.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
    }

}