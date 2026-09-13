package com.project.smartpantry.data.mapper

import com.project.smartpantry.data.remote.dto.MealDto
import com.project.smartpantry.model.Recipe
import com.project.smartpantry.model.RecipeIngredient

//Network -> App
fun MealDto.toRecipe(): Recipe {
    return Recipe(
        id = id,
        name = name,
        category = category,
        area = area,
        thumbnailUrl = thumbnailUrl,
        instructions = instructions,
        ingredients = toRecipeIngredients()
    )
}

private fun MealDto.toRecipeIngredients(): List<RecipeIngredient> {
    val ingredients = listOf(
        ingredient1 to measure1,
        ingredient2 to measure2,
        ingredient3 to measure3,
        ingredient4 to measure4,
        ingredient5 to measure5,
        ingredient6 to measure6,
        ingredient7 to measure7,
        ingredient8 to measure8,
        ingredient9 to measure9,
        ingredient10 to measure10,
        ingredient11 to measure11,
        ingredient12 to measure12,
        ingredient13 to measure13,
        ingredient14 to measure14,
        ingredient15 to measure15,
        ingredient16 to measure16,
        ingredient17 to measure17,
        ingredient18 to measure18,
        ingredient19 to measure19,
        ingredient20 to measure20
    )
    return ingredients.filter { (ingredient, _) -> !ingredient.isNullOrBlank() }
        .map { (ingredient, measure) ->
            RecipeIngredient(name = ingredient!!.trim(), measure = measure?.trim().orEmpty())
        }
}