package ru.tinkoff.news.api

import io.reactivex.Observable
import ru.tinkoff.news.api.interfaces.TinkoffApiService
import ru.tinkoff.news.api.models.*

class TinkoffRepository (val apiService: TinkoffApiService) {

    fun getNews(): Observable<NewsResponse> {
        return apiService.getNews()
    }

    fun getNewsId(newsId: String): Observable<NewsIdResponse> {
        return apiService.getNewsId(newsId)
    }
}

