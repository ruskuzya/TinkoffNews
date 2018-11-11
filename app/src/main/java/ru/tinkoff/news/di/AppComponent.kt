package ru.tinkoff.news.di

import dagger.Component
import ru.tinkoff.news.App
import ru.tinkoff.news.MainActivity
import ru.tinkoff.news.fragments.main.MainFragment
import ru.tinkoff.news.fragments.main.MainPresenter
import ru.tinkoff.news.fragments.news.NewsFragment
import ru.tinkoff.news.fragments.news.NewsPresenter
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, FragmentModule::class])
interface AppComponent {

    fun inject(application: App)

    fun inject(mainActivity: MainActivity)

    fun inject(mainFragment: MainFragment)
    fun inject(mainPresenter: MainPresenter)

    fun inject(newsFragment: NewsFragment)
    fun inject(newsPresenter: NewsPresenter)

}