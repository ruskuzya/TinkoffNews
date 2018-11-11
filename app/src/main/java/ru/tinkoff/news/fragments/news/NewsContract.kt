package ru.tinkoff.news.fragments.news

import ru.tinkoff.news.BaseContract
import ru.tinkoff.news.api.models.NewsIdResponse

interface NewsContract {

    interface View: BaseContract.View {
        fun updateScreen(news: NewsIdResponse)
        fun setRefreshing(refreshing: Boolean)
    }

    interface Presenter: BaseContract.Presenter<NewsContract.View> {
        fun doSync(newsId: String)
    }
}