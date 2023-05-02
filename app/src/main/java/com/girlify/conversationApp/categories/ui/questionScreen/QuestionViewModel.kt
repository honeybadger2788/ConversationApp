package com.girlify.conversationApp.categories.ui.questionScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girlify.conversationApp.categories.domain.GetQuestionsUseCase
import com.girlify.conversationApp.categories.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<*>>(UiState.Loading)
    val uiState: StateFlow<UiState<*>> = _uiState

    fun getQuestions(categoryId: String){
        viewModelScope.launch(Dispatchers.IO) {
            getQuestionsUseCase(categoryId).let{ category ->
                _uiState.value =
                    category?.let { UiState.Success(it) } ?: UiState.Error
            }
        }
    }
}