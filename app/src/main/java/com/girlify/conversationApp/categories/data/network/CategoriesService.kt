package com.girlify.conversationApp.categories.data.network

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

    suspend fun getCategories(): Flow<List<CategoryModel>> {
        val categories = mutableListOf<CategoryModel>()
        val querySnapshot = categoriesCollectionRef.get().await()
        for (document in querySnapshot.documents) {
            val category = document.toCategory()
            categories.add(category)
        }
        return flowOf(categories)
    }
}

private fun DocumentSnapshot.toCategory(): CategoryModel {
    return CategoryModel(this.id, this.data?.get("name") as String,
        this.data!!["questions"] as List<String>
    )
}
