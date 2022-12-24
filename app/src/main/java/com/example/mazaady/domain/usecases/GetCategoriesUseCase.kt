package com.example.mazaady.domain.usecases

import com.example.mazaady.data.entities.DataResult
import com.example.mazaady.data.entities.categories.toDomain
import com.example.mazaady.domain.models.CategoryModel
import com.example.mazaady.domain.repositories.ICategoriesRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val iCategoriesRepository: ICategoriesRepository
) {

    suspend operator fun invoke(): DataResult<List<CategoryModel?>?> {

        return when (val response = iCategoriesRepository.getCategories()) {
            is DataResult.Success -> {

                val categories: List<CategoryModel?>? =
                    response.content?.data?.categories?.map { it?.toDomain() }
                DataResult.Success(categories)
            }
            is DataResult.Error -> DataResult.Error(response.exception)
        }
    }
}