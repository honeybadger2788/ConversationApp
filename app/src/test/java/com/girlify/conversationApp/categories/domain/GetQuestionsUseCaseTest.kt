package com.girlify.conversationApp.categories.domain

import com.girlify.conversationApp.categories.data.CategoryRepository
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class GetQuestionsUseCaseTest {
    @RelaxedMockK
    private lateinit var repository: CategoryRepository

    lateinit var getQuestionsUseCase: GetQuestionsUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getQuestionsUseCase = GetQuestionsUseCase(repository)
    }

    @Test
    fun whenCategoryIsNotFound_thenReturnNull() = runBlocking {
        coEvery { repository.getQuestionsFromDatabase("") } returns null

        val response = getQuestionsUseCase("")

        assert(response == null)
    }

    @Test
    fun whenCategoryIsFoundAndItHasQuestions_thenReturnACategory() = runBlocking {
        val category =
            CategoryModel("categoryTest1", "categoryTest1", "netflix.com", listOf("question1"))
        coEvery { repository.getQuestionsFromDatabase(category.id) } returns category

        val response = getQuestionsUseCase(category.id)

        assert(response == category)
    }

    @Test
    fun whenCategoryIsFoundAndItHasNotQuestions_thenReturnNull() = runBlocking {
        val category =
            CategoryModel("categoryTest1", "categoryTest1", "netflix.com", emptyList())
        coEvery { repository.getQuestionsFromDatabase(category.id) } returns category

        val response = getQuestionsUseCase(category.id)

        assert(response == null)
    }
}