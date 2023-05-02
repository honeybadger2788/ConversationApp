package com.girlify.conversationApp.categories.ui.questionScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girlify.conversationApp.categories.domain.GetQuestionsUseCase
import com.girlify.conversationApp.categories.ui.categoriesScreen.CategoriesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<QuestionsUiState>(QuestionsUiState.Loading)
    val uiState: StateFlow<QuestionsUiState> = _uiState

    fun getQuestions(categoryId: String){
        viewModelScope.launch(Dispatchers.IO) {
            getQuestionsUseCase(categoryId).let{ category ->
                _uiState.value =
                    category?.let { QuestionsUiState.Success(it) } ?: QuestionsUiState.Error
            }
        }
    }
}