package com.project.smartpantry

import android.app.Application
import androidx.room.Room
import com.project.smartpantry.data.local.MIGRATION_1_2
import com.project.smartpantry.data.local.SmartPantryDatabase
import com.project.smartpantry.data.remote.api.MealApiService
import com.project.smartpantry.data.repository.PantryRepository
import com.project.smartpantry.data.repository.RecipeRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@HiltAndroidApp
class SmartPantryApplication : Application()