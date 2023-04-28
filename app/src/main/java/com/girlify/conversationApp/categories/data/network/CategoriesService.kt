package com.girlify.conversationApp.categories.data.network

import com.girlify.conversationApp.categories.data.network.model.CategoryResponse
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import com.girlify.conversationApp.categories.ui.questionScreen.model.QuestionModel
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoriesService @Inject constructor(
    firebaseClient: FirebaseClient
) {
    private val categoriesCollectionRef = firebaseClient.db.collection("categories")

    suspend fun getCategories(): List<CategoryResponse> {
        val categories = mutableListOf<CategoryResponse>()
        val querySnapshot = categoriesCollectionRef.get().await()
        for (document in querySnapshot.documents) {
            val category = document.toResponse()
            categories.add(category)
        }
        return categories
    }
}

private fun DocumentSnapshot.toResponse(): CategoryResponse {
    return CategoryResponse(this.id, this.data?.get("name") as String,
        this.data?.get("image") as String,
        this.data!!["questions"] as List<String>
    )
}
