package com.girlify.conversationApp.categories.ui.questionsScreen

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import com.girlify.conversationApp.categories.ui.questionScreen.QUESTIONS_LIST_TEST_TAG
import com.girlify.conversationApp.categories.ui.questionScreen.QuestionsList
import org.junit.Rule
import org.junit.Test

internal class QuestionsListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

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

    private fun getQuestionsList(): List<String> = listOf(
        "questionTest1",
        "questionTest2",
        "questionTest3"
    )
}