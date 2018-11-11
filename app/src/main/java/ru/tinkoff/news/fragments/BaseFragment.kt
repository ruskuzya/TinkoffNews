package ru.tinkoff.news.fragments

import android.support.v4.app.Fragment

open class BaseFragment : Fragment() {

    fun showError(message : String) {
        val dialog = Dialog.newInstance(message)
        dialog.show(fragmentManager, "dialog")
    }

}