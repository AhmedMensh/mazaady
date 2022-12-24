package com.example.mazaady.data.network

import com.example.mazaady.data.entities.categories.CategoriesResponse
import com.example.mazaady.data.entities.categories.PropertiesResponse
import retrofit2.Response
import retrofit2.http.*

interface MazaadyApiService {

    @GET("/api/get_all_cats")
    suspend fun getCategories(): Response<CategoriesResponse>

    @GET("/api/properties")
    suspend fun getCategoryProperties(
        @Query("cat") categoryId: Int
    ): Response<PropertiesResponse>


}