package ru.tinkoff.news.db.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class NewsObj(idTemp: String? = "", temp: String? = ""): RealmObject() {
    @PrimaryKey
    open var id: String = idTemp!!
    open var title: String = temp!!
}