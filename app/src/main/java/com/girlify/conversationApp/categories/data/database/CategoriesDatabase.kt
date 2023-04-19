package com.girlify.conversationApp.categories.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.girlify.conversationApp.categories.data.database.dao.CategoryDao
import com.girlify.conversationApp.categories.data.database.entity.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1)
abstract class CategoriesDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}