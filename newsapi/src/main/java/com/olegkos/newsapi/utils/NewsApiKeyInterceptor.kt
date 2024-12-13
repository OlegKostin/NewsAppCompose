package com.olegkos.newsapi.utils

import okhttp3.Interceptor
import okhttp3.Response
// перехватываю запрос , который у меня будет происходить chain.request()
/*
модифицирую и добавляю к нему хедер
и таким образом он будет у меня с апи кеем
 */
internal class NewsApiKeyInterceptor(private val apikey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("X-Api-Key", apikey)
                .build()
        )
    }
}