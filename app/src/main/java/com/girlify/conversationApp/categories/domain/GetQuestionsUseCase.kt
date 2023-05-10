package com.girlify.conversationApp.categories.domain

import com.girlify.conversationApp.categories.data.CategoryRepository
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
){
    suspend operator fun invoke(categoryId: String): CategoryModel? {
        val response = categoryRepository.getQuestionsFromDatabase(categoryId)
        return if(response?.questions?.size == 0)
            null
        else
            response
    }
}