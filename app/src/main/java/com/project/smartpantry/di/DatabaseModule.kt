package com.project.smartpantry.di

import android.content.Context
import androidx.room.Room
import com.project.smartpantry.data.local.MIGRATION_1_2
import com.project.smartpantry.data.local.SmartPantryDatabase
import com.project.smartpantry.data.local.dao.IngredientDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton // mark the room database as one application-wide instance
    fun provideDatabase(@ApplicationContext context: Context): SmartPantryDatabase {
        return Room.databaseBuilder(
            context,
            SmartPantryDatabase::class.java,
            "smart_pantry_database"
        ).addMigrations(
            MIGRATION_1_2
        ).build()
    }

    @Provides
    fun provideIngredientDao(database: SmartPantryDatabase): IngredientDao {
        return database.ingredientDao()
    }
}