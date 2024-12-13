package com.olegkos.newsapi

import androidx.annotation.FontRes
import androidx.annotation.IntRange
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.olegkos.newsapi.models.ArticleDto
import com.olegkos.newsapi.models.LanguageDto
import com.olegkos.newsapi.models.ResponseDTO
import com.olegkos.newsapi.models.SortBy
import com.olegkos.newsapi.utils.NewsApiKeyInterceptor
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

interface NewsApi {
    /*
     Api Details https://newsapi.org/docs/endpoints/everything
     */
    @GET("/everything")
    suspend fun everything(
        //https://newsapi.org/docs/endpoints/everything
        @Query("q") query: String? = null,
        @Query("from") dateFrom: Date? = null,
        @Query("to") dateTo: Date? = null,
        @Query("language") languages: List<LanguageDto>? = null,
        @Query("sortBy") sortBy: SortBy? = null,
        // @IntRange аннотация из implementation("androidx.annotation:annotation:1.9.0-beta01")
        @Query("pageSize") @IntRange(from = 0, to = 100) pageSize: Int = 100,
        @Query("page") @IntRange(from = 1) page: Int = 1,
    ): Result<ResponseDTO<ArticleDto>>
//  Response<Article> не возвращать так как надо try catch, можно использовать result
    //Result котлиновский, тогда нужно прокинуть адаптер
    //    implementation "com.github.skydoves:retrofit-adapters-result:1.0.12"
    //Библиотека retrofit-adapters-result изменяет этот подход, чтобы все результаты (как успешные,
// так и ошибочные) возвращались в виде kotlin.Result. Это упрощает обработку ошибок и избавляет от необходимости
// работать с исключениями в каждой точке использования.
}

fun NewsApi(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient? = null,
    json: Json = Json,
): NewsApi {
    return retrofit(baseUrl, apiKey, okHttpClient, json).create()
}

private fun retrofit(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient?,
    json: Json,
): Retrofit {
// перехвативыет запрос, чтобы добавить апикей, в новый окшттпклиет надо только перехватывать
    val modifiedOkHttpClient = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
        .addInterceptor(NewsApiKeyInterceptor(apiKey))
        .build()


    val converterFactory = json.asConverterFactory(MediaType.get("application/json"))
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(ResultCallAdapterFactory.create()) // для result
        .client(modifiedOkHttpClient)
        .build()
    return retrofit
}