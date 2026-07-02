package com.elvin.purple.data.api

import com.elvin.purple.data.model.NewsResponse
import retrofit2.http.GET

interface NewsApiService {
    @GET("v1/cnbc-news/")
    suspend fun getNews(): NewsResponse
}