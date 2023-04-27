package com.girlify.conversationApp.categories.ui.questionsScreen

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.test.espresso.Espresso
import com.girlify.conversationApp.categories.ui.questionScreen.QUESTIONS_LIST_TEST_TAG
import com.girlify.conversationApp.categories.ui.questionScreen.QuestionsList
import org.junit.Rule
import org.junit.Test

class QuestionsListTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    init {
        Espresso.setFailureHandler { error, _ -> throw error }
    }

    @Test
    fun whenComponentStart_thenVerifyListIsEmpty(){
        composeTestRule.setContent {
            QuestionsList(questions = emptyList())
        }

        composeTestRule.onNodeWithTag(QUESTIONS_LIST_TEST_TAG)
            .onChildren()
            .assertCountEquals(0)
    }

    @Test
    fun whenComponentStart_thenVerifyListIsNotEmpty(){
        composeTestRule.setContent {
            QuestionsList(questions = getQuestionsList())
        }

        composeTestRule.onNodeWithTag(QUESTIONS_LIST_TEST_TAG)
            .onChildren()
            .assertCountEquals(getQuestionsList().size)
    }

    @Test
    fun testQuestionsList_scrollable() {
        composeTestRule.setContent {
            QuestionsList(questions = getQuestionsList())
        }

        composeTestRule.onNodeWithTag(QUESTIONS_LIST_TEST_TAG)
            .performTouchInput { swipeLeft() }
    }

    private fun getQuestionsList(): List<String> = listOf(
        "questionTest1",
        "questionTest2",
        "questionTest3"
    )
}