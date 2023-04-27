package com.girlify.conversationApp.categories.ui.questionScreen

import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel

sealed class QuestionsUiState {
    object Loading: QuestionsUiState()
    object Error: QuestionsUiState()
    data class Success(val category: CategoryModel): QuestionsUiState()
}