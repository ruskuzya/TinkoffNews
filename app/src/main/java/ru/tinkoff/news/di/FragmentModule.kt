package ru.tinkoff.news.di

import dagger.Module
import dagger.Provides
import ru.tinkoff.news.fragments.main.MainContract
import ru.tinkoff.news.fragments.main.MainPresenter
import ru.tinkoff.news.fragments.news.NewsContract
import ru.tinkoff.news.fragments.news.NewsPresenter


@Module
class FragmentModule {

    @Provides
    fun provideMainPresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    @Provides
    fun provideNewsPresenter(): NewsContract.Presenter {
        return NewsPresenter()
    }

}