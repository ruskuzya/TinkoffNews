package ru.tinkoff.news.fragments.main

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import retrofit2.HttpException
import ru.tinkoff.news.App
import ru.tinkoff.news.R
import ru.tinkoff.news.api.TinkoffRepositoryProvider
import ru.tinkoff.news.db.models.NewsObj
import java.net.UnknownHostException


class MainPresenter: MainContract.Presenter {

    private lateinit var view: MainContract.View
    private lateinit var context: Context
    private lateinit var realm: Realm

    override fun attach(context: Context, view: MainContract.View) {
        App.appComponent.inject(this)
        this.context = context
        this.view = view
        realm = Realm.getDefaultInstance()
    }

    override fun dettach() {
        realm.close()
    }


    override fun doSync() {
        val repository = TinkoffRepositoryProvider.provideSenatRepository()
        repository.getNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    result ->
                    if (result.resultCode.equals("OK")) {
                        view.setRefreshing(false)
                        var sortedList = result.payload.sortedWith(compareByDescending ({ it.publicationDate!!.milliseconds }))
                        realm.beginTransaction()
                        realm.deleteAll()
                        for (item in sortedList) {
                            var newsObj = NewsObj(item.id,item.text)
                            realm.insert(newsObj)
                        }
                        realm.commitTransaction()
                    }
                }, {
                    error->
                    view.setRefreshing(false)
                    error.printStackTrace()
                    if (error is HttpException) {
                        if (error.code() == 401) {
                            view.showError(context.getString(R.string.error_auth))
                        } else {
                            view.showError(context.getString(R.string.error_unknown))
                        }
                    } else if (error is UnknownHostException) {
                        view.showError(context.getString(R.string.error_internet))
                    } else {
                        view.showError(context.getString(R.string.error_unknown))
                    }

                })
    }
}