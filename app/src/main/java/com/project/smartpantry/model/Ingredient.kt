package com.project.smartpantry.model

data class Ingredient(
    val id: Long,
    val name: String,
    val quantity: Int,
    val unit: String,
    val category: String
)
