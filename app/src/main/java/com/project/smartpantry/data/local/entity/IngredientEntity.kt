package com.project.smartpantry.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val quantity: Int,
    val unit: String,
    val category: String,

    // why not LocalDate, SQLite doesn't have a native LocalDate data type, so we use number of days since 1970-01-01
    val expirationDateEpochDay: Long? = null  // it is optional
)
