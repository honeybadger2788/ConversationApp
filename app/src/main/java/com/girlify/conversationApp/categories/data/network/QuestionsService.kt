package com.girlify.conversationApp.categories.data.network

import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QuestionsService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {
    companion object {
        const val CATEGORY_COLLECTION = "categories"
    }

    suspend fun getQuestionsByCategory(categoryId: String) = runCatching {

        firebaseClient.db
            .collection(CATEGORY_COLLECTION)
            .document(categoryId)
            .get().await()
    }.isSuccess
}