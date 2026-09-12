package com.project.smartpantry

import android.app.Application
import androidx.room.Room
import com.project.smartpantry.data.local.MIGRATION_1_2
import com.project.smartpantry.data.local.SmartPantryDatabase
import com.project.smartpantry.data.remote.api.MealApiService
import com.project.smartpantry.data.repository.PantryRepository
import com.project.smartpantry.data.repository.RecipeRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class SmartPantryApplication : Application() {
    val database: SmartPantryDatabase by lazy {  // don't create the database until something actually asks for it
        Room.databaseBuilder(
            applicationContext,
            SmartPantryDatabase::class.java,
            "smart_pantry_database"
        ).addMigrations(MIGRATION_1_2)
            .build()
    }
    val pantryRepository: PantryRepository by lazy {
        PantryRepository(ingredientDao = database.ingredientDao())
    }
    private val json by lazy {
        Json {
            ignoreUnknownKeys = true  // Parse the fields I declared and safely ignore the rest
        }
    }

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private val mealApiService by lazy {
        retrofit.create(MealApiService::class.java)
    }

    val recipeRepository by lazy {
        RecipeRepository(apiService = mealApiService)
    }
}