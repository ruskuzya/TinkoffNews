package ru.tinkoff.news.api.models

import com.google.gson.annotations.SerializedName

data class NewsDate (
        @SerializedName("milliseconds") val milliseconds: Long
)


data class NewsItem (
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("text") val text: String,
        @SerializedName("publicationDate") val publicationDate: NewsDate,
        @SerializedName("bankInfoTypeId") val bankInfoTypeId: Int
)

data class NewsResponse (val resultCode: String, val payload: List<NewsItem>, val trackingId: String)
