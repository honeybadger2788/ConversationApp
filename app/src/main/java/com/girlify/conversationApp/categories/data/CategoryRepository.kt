package com.girlify.conversationApp.categories.data

import com.girlify.conversationApp.categories.data.database.dao.CategoryDao
import com.girlify.conversationApp.categories.data.database.entity.CategoryEntity
import com.girlify.conversationApp.categories.data.network.CategoriesService
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val firebase: CategoriesService,
    private val categoryDao: CategoryDao
) {
    suspend fun getAllCategoriesFromFirebase(): List<CategoryModel> = firebase.getCategories()

    suspend fun getAllCategoriesFromDatabase(): List<CategoryModel> {
        val response: List<CategoryEntity> = categoryDao.getCategories()
        return response.map {
            it.toDomain()
        }
    }

    suspend fun getQuestionsFromDatabase(categoryId: String): CategoryModel? {
        val response = categoryDao.getQuestions(categoryId)
        return response?.toDomain()
    }

    suspend fun insertCategories(categories: List<CategoryEntity>) =
        categoryDao.insertAll(categories)

    suspend fun deleteAllCategories() = categoryDao.deleteAllQuotes()
}