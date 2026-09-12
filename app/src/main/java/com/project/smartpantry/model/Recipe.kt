package com.project.smartpantry.model

data class Recipe(
    val id: String,
    val name: String,
    val category: String?,
    val area: String?,
    val thumbnailUrl: String?,
    val instructions: String?
)
