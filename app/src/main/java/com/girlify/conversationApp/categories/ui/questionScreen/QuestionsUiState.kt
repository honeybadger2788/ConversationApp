package com.girlify.conversationApp.categories.ui.questionScreen

import com.girlify.conversationApp.categories.ui.questionScreen.model.QuestionModel

sealed interface QuestionsUiState {
    object Loading: QuestionsUiState
    data class Error(val throwable: Throwable): QuestionsUiState
    data class Success(val questions: List<QuestionModel>): QuestionsUiState
}