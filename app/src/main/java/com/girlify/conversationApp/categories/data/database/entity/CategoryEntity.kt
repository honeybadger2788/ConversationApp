package com.girlify.conversationApp.categories.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel

@Entity
data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val image: String,
    val questions: List<String>
) {
    fun toDomain(): CategoryModel {
        return CategoryModel(this.id, this.name, this.image, this.questions)
    }
}