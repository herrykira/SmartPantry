package com.project.smartpantry.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppDestination : NavKey

@Serializable
data object PantryDestination : AppDestination

@Serializable
data object RecipesDestination : AppDestination

@Serializable
data class IngredientDetailDestination(val ingredientId: Long) : AppDestination