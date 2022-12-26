package com.example.mazaady.data.repositories

import com.example.mazaady.data.entities.DataResult
import com.example.mazaady.data.entities.categories.CategoriesResponse
import com.example.mazaady.data.entities.categories.PropertiesResponse
import com.example.mazaady.data.network.RemoteDataSource
import com.example.mazaady.domain.repositories.ICategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ICategoriesRepository {

    override suspend fun getCategories(): DataResult<CategoriesResponse> {
        return remoteDataSource.getCategories()
    }

    override suspend fun getCategoryProperties(subCategoryId: Int): DataResult<PropertiesResponse> {
        return remoteDataSource.getCategoryProperties(subCategoryId)
    }

    override suspend fun getOptionProperties(optionId: Int): DataResult<PropertiesResponse> {
        return remoteDataSource.getOptionProperties(optionId)
    }
}