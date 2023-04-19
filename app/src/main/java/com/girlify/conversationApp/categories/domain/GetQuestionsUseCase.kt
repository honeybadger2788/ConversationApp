package com.girlify.conversationApp.categories.domain

import com.girlify.conversationApp.categories.data.CategoryRepository
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
){
    suspend operator fun invoke(categoryId: String): Flow<CategoryModel?> {
        return categoryRepository.getQuestionsFromDatabase(categoryId)
    }
}