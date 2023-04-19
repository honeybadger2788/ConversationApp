package com.girlify.conversationApp.categories.ui.questionScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girlify.conversationApp.categories.domain.GetQuestionsUseCase
import com.girlify.conversationApp.categories.ui.categoriesScreen.CategoriesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
): ViewModel() {


}