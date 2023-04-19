package com.girlify.conversationApp.categories.ui.categoriesScreen.model

import com.girlify.conversationApp.categories.data.database.entity.CategoryEntity

data class CategoryModel(
    val id: String,
    val name: String,
    val questions: List<String>
) {
    fun toDatabase(): CategoryEntity {
        return CategoryEntity(this.id, this.name, this.questions)
    }
}
