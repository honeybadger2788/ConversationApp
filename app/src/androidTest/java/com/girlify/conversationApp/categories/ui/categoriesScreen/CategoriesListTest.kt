package com.girlify.conversationApp.categories.ui.categoriesScreen

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import org.junit.Rule
import org.junit.Test

internal class CategoriesListTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun whenComponentStart_thenVerifyListIsEmpty(){
        composeTestRule.setContent {
            CategoriesList(categories = emptyList(), goToQuestions = {})
        }

        composeTestRule.onNodeWithTag(CATEGORIES_LIST_TEST_TAG)
            .onChildren()
            .assertCountEquals(0)
    }

    @Test
    fun whenComponentStart_thenVerifyListIsNotEmpty(){
        composeTestRule.setContent {
            CategoriesList(categories = getCategoriesList(), goToQuestions = {})
        }

        composeTestRule.onNodeWithTag(CATEGORIES_LIST_TEST_TAG)
            .onChildren()
            .assertCountEquals(getCategoriesList().size)
    }
    @Test
    fun whenComponentStart_thenVerifyFirstAndLastCategoriesName(){
        composeTestRule.setContent {
            CategoriesList(categories = getCategoriesList(), goToQuestions = {})
        }

        getCategoriesList().forEach {
            composeTestRule.onNodeWithText(text = it.name).assertIsDisplayed()
        }
    }

    private fun getCategoriesList(): List<CategoryModel> = listOf(
        CategoryModel("categoryTest1", "categoryTest1","netflix.com", emptyList()),
        CategoryModel("categoryTest2", "categoryTest2","netflix.com", emptyList()),
        CategoryModel("categoryTest3", "categoryTest3","netflix.com", emptyList()),
    )
}