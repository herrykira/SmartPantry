package com.project.smartpantry.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.smartpantry.data.local.dao.IngredientDao
import com.project.smartpantry.data.local.entity.IngredientEntity

@Database(entities = [IngredientEntity::class], version = 2, exportSchema = false)  //Room + KSP generates the actual database implementation during compilation
abstract class SmartPantryDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao
}