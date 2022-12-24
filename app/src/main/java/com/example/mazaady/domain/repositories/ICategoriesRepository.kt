package com.example.mazaady.domain.repositories

import com.example.mazaady.data.entities.DataResult
import com.example.mazaady.data.entities.categories.CategoriesResponse
import com.example.mazaady.data.entities.categories.PropertiesResponse

interface ICategoriesRepository {

    suspend fun getCategories(): DataResult<CategoriesResponse> {
        TODO("Implement your code")
    }

    suspend fun getCategoryProperties(subCategoryId: Int): DataResult<PropertiesResponse> {
        TODO("Implement your code")
    }
}