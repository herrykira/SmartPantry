package com.project.smartpantry

import android.app.Application
import androidx.room.Room
import com.project.smartpantry.data.local.MIGRATION_1_2
import com.project.smartpantry.data.local.SmartPantryDatabase
import com.project.smartpantry.data.repository.PantryRepository

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
}