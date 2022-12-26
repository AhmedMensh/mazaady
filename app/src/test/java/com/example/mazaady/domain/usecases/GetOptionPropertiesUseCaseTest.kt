package com.example.mazaady.domain.usecases

import com.example.mazaady.data.entities.DataResult
import com.example.mazaady.data.entities.categories.PropertiesResponse
import com.example.mazaady.domain.repositories.ICategoriesRepository
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test


internal class GetOptionPropertiesUseCaseTest{
    @Test
    fun getOptionProperties_thenReturnSuccessData() = runBlocking{

        val iCategoriesRepository = object : ICategoriesRepository {

            override suspend fun getOptionProperties(optionId: Int): DataResult<PropertiesResponse> {
                return DataResult.Success(PropertiesResponse())
            }
        }

        val response = GetOptionPropertiesUseCase(iCategoriesRepository).invoke(optionId = 1)

        assertTrue(response is DataResult.Success)
    }

    @Test
    fun getOptionProperties_thenReturnErrorModel() = runBlocking{

        val iCategoriesRepository = object : ICategoriesRepository {

            override suspend fun getOptionProperties(optionId: Int): DataResult<PropertiesResponse> {
                return DataResult.Error(Exception())
            }
        }

        val response = GetOptionPropertiesUseCase(iCategoriesRepository).invoke(optionId = -1)

        assertTrue(response is DataResult.Error)
    }


}