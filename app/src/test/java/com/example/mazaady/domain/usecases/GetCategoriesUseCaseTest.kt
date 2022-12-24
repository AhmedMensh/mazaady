package com.example.mazaady.domain.usecases

import com.example.mazaady.data.entities.DataResult
import com.example.mazaady.data.entities.categories.CategoriesResponse
import com.example.mazaady.domain.repositories.ICategoriesRepository
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test


internal class GetCategoriesUseCaseTest {


    @Test
    fun getAllCategories_thenReturnSuccessData() = runBlocking {

        val iCategoriesRepository = object : ICategoriesRepository {

            override suspend fun getCategories(): DataResult<CategoriesResponse> {

                return DataResult.Success(CategoriesResponse())
            }

        }

        val response = GetCategoriesUseCase(iCategoriesRepository).invoke()

        assertTrue(response is DataResult.Success)
    }


    @Test
    fun getAllCategories_thenReturnErrorModel() = runBlocking {

        val iCategoriesRepository = object : ICategoriesRepository {

            override suspend fun getCategories(): DataResult<CategoriesResponse> {
                return DataResult.Error(Exception(""))
            }
        }

        val response = GetCategoriesUseCase(iCategoriesRepository).invoke()

        assertTrue(response is DataResult.Error)
    }


}