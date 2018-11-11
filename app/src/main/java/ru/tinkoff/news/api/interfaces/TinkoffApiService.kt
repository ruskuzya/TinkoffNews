package ru.tinkoff.news.api.interfaces

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.tinkoff.news.BuildConfig
import ru.tinkoff.news.api.models.*


interface TinkoffApiService {

    @GET("/v1/news")
    fun getNews(): Observable<NewsResponse>

    @GET("/v1/news_content")
    fun getNewsId(@Query("id") newsId : String): Observable<NewsIdResponse>

    /**
     * Companion object to create the ApiService
     */
    companion object Factory {

        private var gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'")
                .create()

        fun create(client: OkHttpClient): TinkoffApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BuildConfig.ENDPOINT)
                    .client(client)
                    .build()

            return retrofit.create(TinkoffApiService::class.java)
        }
    }
}