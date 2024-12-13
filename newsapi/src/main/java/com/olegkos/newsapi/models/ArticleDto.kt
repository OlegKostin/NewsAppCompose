package com.olegkos.newsapi.models

import com.olegkos.newsapi.utils.DateTimeFormatUTCSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class ArticleDto(
    @SerialName("source") val source: Source,
    @SerialName("author") val author: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("url") val url: String,
    @SerialName("urlToImage") val urlToImage: String,
    @Serializable(with = DateTimeFormatUTCSerializer::class)
    @SerialName("publishedAt") val publishedAt: Date,
    @SerialName("content") val content: String,

    )
