package com.girlify.conversationApp.categories.domain

import com.girlify.conversationApp.categories.data.CategoryRepository
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class GetCategoriesUseCaseTest{

    @RelaxedMockK
    private lateinit var repository: CategoryRepository

    lateinit var getCategoriesUseCase: GetCategoriesUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getCategoriesUseCase = GetCategoriesUseCase(repository)
    }

    @Test
    fun whenDatabaseDoesntReturnAnything_thenGetValuesFromFirebase() = runBlocking {
        coEvery { repository.getAllCategoriesFromDatabase() } returns emptyList()

        getCategoriesUseCase()

        coVerify( exactly = 1) {
            repository.getAllCategoriesFromFirebase()
        }

        coVerify ( exactly = 1) {
            repository.insertCategories(any())
        }
    }

    @Test
    fun whenDatabaseReturnSomething_thenDoesntCallFirebase() = runBlocking {
        coEvery { repository.getAllCategoriesFromDatabase() } returns getCategoriesList()

        val response = getCategoriesUseCase()

        coVerify( exactly = 0) {
            repository.getAllCategoriesFromFirebase()
        }

        response.collect {
            assert(getCategoriesList() == it)
        }
    }

    private fun getCategoriesList(): List<CategoryModel> = listOf(
        CategoryModel("categoryTest1", "categoryTest1","netflix.com", emptyList()),
        CategoryModel("categoryTest2", "categoryTest2","netflix.com", emptyList()),
        CategoryModel("categoryTest3", "categoryTest3","netflix.com", emptyList()),
    )
}