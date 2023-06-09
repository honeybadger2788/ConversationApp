package com.girlify.conversationApp.categories.ui.categoriesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girlify.conversationApp.categories.domain.GetCategoriesUseCase
import com.girlify.conversationApp.categories.ui.UiState
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<*>>(UiState.Loading)
    val uiState: StateFlow<UiState<*>> = _uiState

    fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            getCategoriesUseCase().let {
                _uiState.value = UiState.Success(it)
            }
        }
    }
}