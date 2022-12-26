package com.example.mazaady.presentation.first_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mazaady.data.entities.DataResult
import com.example.mazaady.domain.models.CategoryModel
import com.example.mazaady.domain.models.PropertyModel
import com.example.mazaady.domain.models.PropertyOptionModel
import com.example.mazaady.domain.models.SubCategoryModel
import com.example.mazaady.domain.usecases.GetCategoriesUseCase
import com.example.mazaady.domain.usecases.GetOptionPropertiesUseCase
import com.example.mazaady.domain.usecases.GetSubCategoryPropertiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Abdullah Abo El~Makarem on 26/06/2022.
 */
@HiltViewModel
class FirstScreenViewModel @Inject constructor(
    private val categoriesUseCase: GetCategoriesUseCase,
    private val subCategoryPropertiesUseCase: GetSubCategoryPropertiesUseCase,
    private val getOptionPropertiesUseCase: GetOptionPropertiesUseCase
) :
    ViewModel() {


    private val _uiState: MutableStateFlow<FirstScreenUiState> =
        MutableStateFlow(FirstScreenUiState.Loading(true))
    val uiState: StateFlow<FirstScreenUiState> = _uiState

    private var categories: List<CategoryModel?>? = emptyList()
    private var subCategories: List<SubCategoryModel?>? = emptyList()
    private var options: List<PropertyOptionModel?>? = emptyList()
    private var properties: MutableList<PropertyModel?>? = mutableListOf()
    private var currentSelectedProperty: PropertyModel? = null

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            _uiState.emit(FirstScreenUiState.Loading(true))
            when (val result = categoriesUseCase()) {
                is DataResult.Success -> {
                    categories = result.content
                    _uiState.emit(FirstScreenUiState.Loading(false))
                }
                is DataResult.Error -> {
                    _uiState.emit(FirstScreenUiState.Error(result.exception.message.orEmpty()))
                    _uiState.emit(FirstScreenUiState.Loading(false))
                }
            }
        }
    }


    private fun getOptionProperties(option: PropertyOptionModel, property: PropertyModel?) {
        viewModelScope.launch {
            _uiState.emit(FirstScreenUiState.Loading(true))
            val propertyIndex = properties?.indexOf(property)?.plus(1) ?: 0
            if (option.id == -1) {
                val otherProperty =
                    PropertyModel(
                        "", -1,
                        false,
                        "Specify",
                        slug = "",
                        type = "", value = ""
                    )
                property?.children = listOf(otherProperty)
                properties?.addAll(propertyIndex, listOf(otherProperty))
                _uiState.emit(FirstScreenUiState.ShowPropertiesList(properties))
            } else
                when (val result = getOptionPropertiesUseCase(option.id)) {
                    is DataResult.Success -> {
                        property?.children = result.content

                        result.content?.let { properties?.addAll(propertyIndex, it) }
                        _uiState.emit(FirstScreenUiState.ShowPropertiesList(properties))
                        _uiState.emit(FirstScreenUiState.Loading(false))
                    }
                    is DataResult.Error -> {
                        _uiState.emit(FirstScreenUiState.Error(result.exception.message.orEmpty()))
                        _uiState.emit(FirstScreenUiState.Loading(false))
                    }
                }
        }
    }

    private fun getCategoryProperties(subCategoryId: Int) {
        viewModelScope.launch {
            _uiState.emit(FirstScreenUiState.Loading(true))
            when (val result = subCategoryPropertiesUseCase(subCategoryId)) {
                is DataResult.Success -> {
                    properties = result.content?.toMutableList()
                    _uiState.emit(FirstScreenUiState.Loading(false))
                    _uiState.emit(FirstScreenUiState.ShowPropertiesList(result.content))
                }
                is DataResult.Error -> {
                    _uiState.emit(FirstScreenUiState.Error(result.exception.message.orEmpty()))
                    _uiState.emit(FirstScreenUiState.Loading(false))
                }
            }
        }
    }

    fun handelEvents(event: FirstScreenUIEvent) {
        when (event) {
            is FirstScreenUIEvent.OnCategoriesFieldClicked -> {
                viewModelScope.launch {
                    _uiState.emit(FirstScreenUiState.ShowCategoriesBottomSheet(categories))
                }

            }
            is FirstScreenUIEvent.OnCategorySelected -> {
                if (!event.subCategories.isNullOrEmpty()) {
                    subCategories = event.subCategories
                    viewModelScope.launch {
                        _uiState.emit(FirstScreenUiState.ShowSubCategoryField)
                    }
                }
            }
            is FirstScreenUIEvent.OnSubCategoriesFieldClicked -> {

                viewModelScope.launch {
                    _uiState.emit(FirstScreenUiState.ShowSubCategoriesBottomSheet(subCategories))
                }
            }
            is FirstScreenUIEvent.OnPropertyFieldClicked -> {
                options = event.property.options

                currentSelectedProperty = event.property
                viewModelScope.launch {
                    _uiState.emit(FirstScreenUiState.ShowOptionsBottomSheet(options))
                }
            }
            is FirstScreenUIEvent.OnSubCategorySelected -> {
                getCategoryProperties(event.subCategoryId)
            }
            is FirstScreenUIEvent.OnPropertyOptionSelected -> {
                val property = properties?.firstOrNull { it?.id == currentSelectedProperty?.id }
                property?.selectedOptionModel = event.option
                property?.options?.map { it?.isSelected = false }
                property?.options?.firstOrNull { it?.id == event.option.id }?.isSelected = true
                property?.let { removePropertyChildren(it) }
                getOptionProperties(event.option, property)

            }
            is FirstScreenUIEvent.OnPropertyFieldTextChanged -> {
                currentSelectedProperty = event.property
                properties?.find { it?.id == currentSelectedProperty?.id }?.selectedOptionModel =
                    PropertyOptionModel(
                        id = -1,
                        child = false,
                        name = event.value,
                        parent = 0,
                        slug = "",
                        isOtherOption = true
                    )
            }
            is FirstScreenUIEvent.OnShowResultButtonClicked -> {
                val string = java.lang.StringBuilder()
                properties?.forEach {
                    string.append("${it?.name} => ${it?.selectedOptionModel?.name}\n")
                }
                viewModelScope.launch {
                    _uiState.emit(FirstScreenUiState.ShowResult(string.toString()))
                }
            }

        }
    }

    private fun removePropertyChildren(property: PropertyModel?) {
        if (property != null) property.children?.forEach { removePropertyChildren(it) }
        property?.children?.let { properties?.removeAll(it) }
    }
}


sealed class FirstScreenUiState {
    class Loading(val value: Boolean) : FirstScreenUiState()
    class Error(val error: String) : FirstScreenUiState()
    class ShowCategoriesBottomSheet(val categories: List<CategoryModel?>?) : FirstScreenUiState()
    class ShowOptionsBottomSheet(val options: List<PropertyOptionModel?>?) : FirstScreenUiState()
    class ShowSubCategoriesBottomSheet(val categories: List<SubCategoryModel?>?) :
        FirstScreenUiState()

    class ShowPropertiesList(val properties: List<PropertyModel?>?) : FirstScreenUiState()
    object ShowSubCategoryField : FirstScreenUiState()
    class ShowResult(val result: String) : FirstScreenUiState()
}

sealed interface FirstScreenUIEvent {
    object OnCategoriesFieldClicked : FirstScreenUIEvent
    class OnPropertyFieldClicked(val property: PropertyModel) : FirstScreenUIEvent
    class OnPropertyFieldTextChanged(val property: PropertyModel, val value: String) :
        FirstScreenUIEvent

    class OnPropertyOptionSelected(val option: PropertyOptionModel) : FirstScreenUIEvent
    object OnSubCategoriesFieldClicked : FirstScreenUIEvent
    object OnShowResultButtonClicked : FirstScreenUIEvent

    class OnCategorySelected(val subCategories: List<SubCategoryModel?>?) : FirstScreenUIEvent
    class OnSubCategorySelected(val subCategoryId: Int) : FirstScreenUIEvent
}