package com.example.mazaady.data.network

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val mazaadyApiService: MazaadyApiService,
    private val safeApiCall: SafeApiCall
) {

    suspend fun getCategories() = safeApiCall { mazaadyApiService.getCategories() }
    suspend fun getCategoryProperties(subCategoryId : Int) = safeApiCall { mazaadyApiService.getCategoryProperties(subCategoryId) }
    suspend fun getOptionProperties(optionId : Int) = safeApiCall { mazaadyApiService.getOptionProperties(optionId) }
}