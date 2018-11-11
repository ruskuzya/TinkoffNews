package ru.tinkoff.news.events

import android.support.v4.app.Fragment

class GoToFragment {

    constructor(fragment_class: Fragment, fragment_type: String, toBack: Boolean) {
        m_fragment_class = fragment_class
        m_fragment_type = fragment_type
        m_toBack = toBack
    }

    companion object {
        var m_fragment_class: Fragment? = null
        var m_fragment_type: String = ""
        var m_toBack: Boolean = false
    }
}