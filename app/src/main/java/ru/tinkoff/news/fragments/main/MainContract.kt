package ru.tinkoff.news.fragments.main

import ru.tinkoff.news.BaseContract

interface MainContract {

    interface View: BaseContract.View {
        fun setRefreshing(refreshing: Boolean)
    }

    interface Presenter: BaseContract.Presenter<MainContract.View> {
        fun doSync()
    }
}