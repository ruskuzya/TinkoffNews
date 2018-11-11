package ru.tinkoff.news.commons

import android.content.SharedPreferences
import javax.inject.Inject


class MySharedPreferences @Inject constructor(private val mSharedPreferences: SharedPreferences) {

    fun putData(key: String, value: String) {
        mSharedPreferences.edit().putString(key, value).apply()
    }

    fun getData(key: String): String {
        return mSharedPreferences.getString(key, "")
    }

    fun putStringSet(key: String, value: Set<String>) {
        mSharedPreferences.edit().putStringSet(key, value).apply()
    }

    fun getStringSet(key: String): Set<String> {
        return mSharedPreferences.getStringSet(key, HashSet())
    }
}