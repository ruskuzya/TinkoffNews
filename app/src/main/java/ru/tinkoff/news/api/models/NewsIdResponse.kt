package ru.tinkoff.news.api.models

import com.google.gson.annotations.SerializedName

data class Payload (
        @SerializedName("title") val title: NewsItem,
        @SerializedName("creationDate") val creationDate: NewsDate,
        @SerializedName("lastModificationDate") val lastModificationDate: NewsDate,
        @SerializedName("content") val content: String,
        @SerializedName("bankInfoTypeId") val bankInfoTypeId: Int,
        @SerializedName("typeId") val typeId: String
)

data class NewsIdResponse (val resultCode: String, val trackingId: String, val payload: Payload)
