package com.girlify.conversationApp.categories.data.database.di

import android.content.Context
import androidx.room.Room
import com.girlify.conversationApp.categories.data.database.dao.CategoryDao
import com.girlify.conversationApp.categories.data.database.CategoriesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideCategoryDao(categoriesDatabase: CategoriesDatabase): CategoryDao {
        return categoriesDatabase.categoryDao()
    }

    @Provides
    @Singleton
    fun provideConversationDatabase(@ApplicationContext appContext: Context): CategoriesDatabase {
        return Room.databaseBuilder(
            appContext,
            CategoriesDatabase::class.java,
            "CategoriesDatabase"
        ).build()
    }
}