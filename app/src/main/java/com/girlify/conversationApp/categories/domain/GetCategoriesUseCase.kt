package com.girlify.conversationApp.categories.domain

import com.girlify.conversationApp.categories.data.CategoryRepository
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): List<CategoryModel> {
        val categories = categoryRepository.getAllCategoriesFromDatabase()
        return categories.ifEmpty {
            val response = categoryRepository.getAllCategoriesFromFirebase()
            categoryRepository.insertCategories(response.map { category -> category.toDatabase() })
            response
        }
    }
}

