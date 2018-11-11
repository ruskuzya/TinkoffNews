package ru.tinkoff.news

import android.content.Context

class BaseContract {

    interface Presenter<in T> {
        fun attach(context: Context, view: T)
        fun dettach()
    }

    interface View {
        fun showError(message:String)
    }
}