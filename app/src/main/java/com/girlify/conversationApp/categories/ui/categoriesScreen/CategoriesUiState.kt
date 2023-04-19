package com.girlify.conversationApp.categories.ui.categoriesScreen

import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel

sealed interface CategoriesUiState {
    object Loading: CategoriesUiState
    data class Error(val throwable: Throwable): CategoriesUiState
    data class Success(val categories: List<CategoryModel>): CategoriesUiState
}