package com.girlify.conversationApp.categories.data.network.model

import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel

data class CategoryResponse(
    val id: String,
    val name: String,
    val image: String,
    val questions: List<String>
) {
    fun toDomain(): CategoryModel {
        return CategoryModel(this.id, this.name, this.image, this.questions)
    }
}