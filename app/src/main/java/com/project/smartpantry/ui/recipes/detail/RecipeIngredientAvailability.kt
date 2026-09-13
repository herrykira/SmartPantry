package com.project.smartpantry.ui.recipes.detail

import com.project.smartpantry.model.RecipeIngredient

data class RecipeIngredientAvailability(
    val ingredient: RecipeIngredient,
    val isInPantry: Boolean
)
