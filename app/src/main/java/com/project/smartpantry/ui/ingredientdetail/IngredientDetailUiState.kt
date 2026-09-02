package com.project.smartpantry.ui.ingredientdetail

import com.project.smartpantry.model.Ingredient

/*
* isLoading = true -> Room hasn't emitted yet
*
* isLoading = false + ingredient != null -> show ingredient
*
* isLoading = false + ingredient == null -> ingredient doesn't exist
* */
data class IngredientDetailUiState(
    val ingredient: Ingredient? = null,
    val isLoading: Boolean = true
)