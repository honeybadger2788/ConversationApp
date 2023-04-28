package com.girlify.conversationApp.categories.ui

sealed class UiState<T>{
    object Loading: UiState<Nothing>()
    object Error: UiState<Nothing>()
    data class Success<T>(val data: T): UiState<T>()
}
