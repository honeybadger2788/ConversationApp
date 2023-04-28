package com.girlify.conversationApp.categories.ui.categoriesScreen

import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel

sealed class CategoriesUiState {
    object Loading: CategoriesUiState()
    object Error: CategoriesUiState()
    data class Success(val categories: List<CategoryModel>): CategoriesUiState()
}