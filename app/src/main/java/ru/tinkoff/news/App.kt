package ru.tinkoff.news

import android.app.Application
import android.content.Context
import io.realm.Realm
import ru.tinkoff.news.commons.MySharedPreferences
import ru.tinkoff.news.di.AppComponent
import ru.tinkoff.news.di.AppModule
import ru.tinkoff.news.di.DaggerAppComponent
import javax.inject.Inject


class App : Application() {

    companion object {
        @JvmStatic lateinit var appComponent: AppComponent
    }

    @Inject
    lateinit var sharedPreferences: MySharedPreferences

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        appComponent.inject(this)

        println("App: $sharedPreferences")
    }

    fun appContext():Context {
        return applicationContext
    }

}