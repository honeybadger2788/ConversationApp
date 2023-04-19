package com.girlify.conversationApp.categories.ui.categoriesScreen.model

import com.girlify.conversationApp.categories.ui.questionScreen.model.QuestionModel

data class CategoryModel(val id: String, val name: String, val questions: List<QuestionModel>)
