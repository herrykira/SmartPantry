package com.project.smartpantry.data.mapper

import com.project.smartpantry.data.remote.dto.MealDto
import com.project.smartpantry.model.Recipe

//Network -> App
fun MealDto.toRecipe(): Recipe {
    return Recipe(
        id = id,
        name = name,
        category = category,
        area = area,
        thumbnailUrl = thumbnailUrl,
        instructions = instructions
    )
}