package com.example.mazaady.domain.usecases

import com.example.mazaady.data.entities.DataResult
import com.example.mazaady.data.entities.categories.PropertiesResponse
import com.example.mazaady.domain.repositories.ICategoriesRepository
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test


internal class GetSubCategoryPropertiesUseCaseTest{
    @Test
    fun getCategoryProperties_thenReturnSuccessData() = runBlocking{

        val iCategoriesRepository = object : ICategoriesRepository {

            override suspend fun getCategoryProperties(subCategoryId: Int): DataResult<PropertiesResponse> {
                return DataResult.Success(PropertiesResponse())
            }
        }

        val response = GetSubCategoryPropertiesUseCase(iCategoriesRepository).invoke(subCategoryId = 1)

        assertTrue(response is DataResult.Success)
    }

    @Test
    fun getCategoryProperties_thenReturnErrorModel() = runBlocking{

        val iCategoriesRepository = object : ICategoriesRepository {

            override suspend fun getCategoryProperties(subCategoryId: Int): DataResult<PropertiesResponse> {
                return DataResult.Error(Exception())
            }
        }

        val response = GetSubCategoryPropertiesUseCase(iCategoriesRepository).invoke(subCategoryId = -1)

        assertTrue(response is DataResult.Error)
    }


}