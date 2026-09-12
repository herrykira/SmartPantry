package com.project.smartpantry.ui.recipes

import com.project.smartpantry.model.Recipe

/*Represent every possible state of a recipe search explicitly
* main benefits of a sealed state hierarchy: Kotlin forces us to handle every case.*/
sealed interface RecipesSearchState {
    data object Idle : RecipesSearchState
    data object Loading : RecipesSearchState
    data class Success(val recipes: List<Recipe>) : RecipesSearchState
    data class Error(val message: String) : RecipesSearchState
}