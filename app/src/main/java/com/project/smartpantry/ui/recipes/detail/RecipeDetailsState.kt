package com.project.smartpantry.ui.recipes.detail

import com.project.smartpantry.model.Recipe
/*
* Represent loading, success, missing recipe, and network failure explicitly.
* */
interface RecipeDetailsState {
    data object Loading : RecipeDetailsState
    data class Success(val recipe: Recipe) : RecipeDetailsState
    data object NotFound : RecipeDetailsState
    data class Error(val message: String) : RecipeDetailsState
}