package com.girlify.conversationApp.categories.data.network

import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoriesService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {
    companion object {
        const val CATEGORY_COLLECTION = "categories"
    }

    suspend fun getCategories() = runCatching {
        firebaseClient.db
            .collection(CATEGORY_COLLECTION)
            .get().await()
    }.isSuccess
}