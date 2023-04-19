package com.girlify.conversationApp.categories.domain

import com.girlify.conversationApp.categories.data.network.CategoriesService
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoriesService: CategoriesService
) {
    suspend operator fun invoke(): Flow<List<CategoryModel>> = categoriesService.getCategories()
}

