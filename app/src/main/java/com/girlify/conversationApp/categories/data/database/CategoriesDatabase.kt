package com.girlify.conversationApp.categories.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.girlify.conversationApp.categories.data.database.converter.Converters
import com.girlify.conversationApp.categories.data.database.dao.CategoryDao
import com.girlify.conversationApp.categories.data.database.entity.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class CategoriesDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}