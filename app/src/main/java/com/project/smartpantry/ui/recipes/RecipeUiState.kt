package com.project.smartpantry.ui.recipes

import com.project.smartpantry.model.Recipe

data class RecipeUiState(
    val searchQuery: String = "",
    val searchState: RecipesSearchState = RecipesSearchState.Idle
)
