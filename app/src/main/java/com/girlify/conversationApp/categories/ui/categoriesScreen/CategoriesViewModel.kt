package com.girlify.conversationApp.categories.ui.categoriesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girlify.conversationApp.categories.domain.GetCategoriesUseCase
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<CategoriesUiState>(CategoriesUiState.Loading)
    val uiState: StateFlow<CategoriesUiState> = _uiState

    fun getCategories(){
        viewModelScope.launch {
            getCategoriesUseCase().collect {
                _uiState.value = CategoriesUiState.Success(it)
            }
        }
    }
}