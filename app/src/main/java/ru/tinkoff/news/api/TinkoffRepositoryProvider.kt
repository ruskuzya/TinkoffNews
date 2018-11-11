package ru.tinkoff.news.api

import okhttp3.OkHttpClient
import ru.tinkoff.news.api.interfaces.TinkoffApiService

object TinkoffRepositoryProvider {

    fun provideSenatRepository(): TinkoffRepository {

        val client = OkHttpClient.Builder().build()

        return TinkoffRepository(TinkoffApiService.Factory.create(client))
    }
}