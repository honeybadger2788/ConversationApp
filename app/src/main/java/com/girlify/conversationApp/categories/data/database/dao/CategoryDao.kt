package com.girlify.conversationApp.categories.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.girlify.conversationApp.categories.data.database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM CategoryEntity")
    suspend fun getCategories(): List<CategoryEntity>

    @Query("SELECT * FROM CategoryEntity WHERE id = :categoryId")
    suspend fun getQuestions(categoryId: String): CategoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories:List<CategoryEntity>)

    @Query("DELETE FROM CategoryEntity")
    suspend fun deleteAllQuotes()
}