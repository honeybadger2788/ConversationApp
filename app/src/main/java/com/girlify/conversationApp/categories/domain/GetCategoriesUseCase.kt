package com.girlify.conversationApp.categories.domain

import com.girlify.conversationApp.categories.data.CategoryRepository
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): Flow<List<CategoryModel>> {
        val categories = categoryRepository.getAllCategoriesFromDatabase()
        return if (categories.isEmpty()) {
            val response = categoryRepository.getAllCategoriesFromFirebase()
            categoryRepository.insertCategories(response.map { category -> category.toDatabase() })
            flowOf(response)
        } else {
            flowOf(categories)
        }
    }
}

