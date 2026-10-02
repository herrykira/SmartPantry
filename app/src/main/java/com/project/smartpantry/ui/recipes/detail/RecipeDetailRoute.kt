package com.project.smartpantry.ui.recipes.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/*connect Navigation, ViewModel, repository and screen*/
@Composable
fun RecipeDetailRoute(recipeId: String, onBack: () -> Unit) {
    val recipeDetailsViewModel: RecipeDetailViewModel =
        hiltViewModel<RecipeDetailViewModel, RecipeDetailViewModel.Factory>(
            creationCallback = { factory ->
                factory.create(recipeId = recipeId)
            }
        )
    val uiState by recipeDetailsViewModel.uiState.collectAsStateWithLifecycle()

    RecipeDetailScreen(uiState = uiState, onBack = onBack)
}