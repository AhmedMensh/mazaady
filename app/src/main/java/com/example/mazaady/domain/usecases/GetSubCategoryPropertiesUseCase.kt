package com.example.mazaady.domain.usecases

import com.example.mazaady.data.entities.DataResult
import com.example.mazaady.data.entities.categories.toDomain
import com.example.mazaady.domain.models.PropertyModel
import com.example.mazaady.domain.repositories.ICategoriesRepository
import javax.inject.Inject

class GetSubCategoryPropertiesUseCase @Inject constructor(
    private val iCategoriesRepository: ICategoriesRepository
) {

    suspend operator fun invoke(subCategoryId: Int): DataResult<List<PropertyModel?>?> {

        return when (val response = iCategoriesRepository.getCategoryProperties(subCategoryId)) {
            is DataResult.Success -> {

                val properties: List<PropertyModel?>? =
                    response.content?.data?.map { it?.toDomain() }
                DataResult.Success(properties)
            }
            is DataResult.Error -> DataResult.Error(response.exception)
        }
    }
}