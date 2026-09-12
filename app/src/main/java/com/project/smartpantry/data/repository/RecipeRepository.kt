package com.project.smartpantry.data.repository

import com.project.smartpantry.data.mapper.toRecipe
import com.project.smartpantry.data.remote.api.MealApiService
import com.project.smartpantry.model.Recipe

class RecipeRepository(private val apiService: MealApiService) {
    suspend fun searchRecipes(query: String): List<Recipe> {
        val response = apiService.searchMeals(query = query)
        return response.meals.orEmpty().map { mealDto -> mealDto.toRecipe() }
    }
}