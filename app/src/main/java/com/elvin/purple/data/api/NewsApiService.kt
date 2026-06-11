package com.elvin.purple.data.api

import com.elvin.purple.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    // Mengambil berita dari CNBC Indonesia
    @GET("v1/cnbc-news/")
    suspend fun getNews(): NewsResponse
}