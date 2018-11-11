package ru.tinkoff.news.fragments.news

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import ru.tinkoff.news.App
import ru.tinkoff.news.R
import ru.tinkoff.news.api.TinkoffRepositoryProvider
import java.net.UnknownHostException

class NewsPresenter: NewsContract.Presenter {

    private lateinit var view: NewsContract.View
    private lateinit var context: Context

    override fun attach(context: Context, view: NewsContract.View) {
        App.appComponent.inject(this)
        this.context = context
        this.view = view
    }

    override fun dettach() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doSync(newsId: String) {
        val repository = TinkoffRepositoryProvider.provideSenatRepository()
        repository.getNewsId(newsId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    result ->
                    if (view!=null) {
                        view.setRefreshing(false)
                        view.updateScreen(result)
                    }
                }, {
                    error->
                    if (view!=null) {
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
                    }
                })
    }


}