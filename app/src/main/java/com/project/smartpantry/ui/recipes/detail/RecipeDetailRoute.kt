package com.project.smartpantry.ui.recipes.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.smartpantry.SmartPantryApplication

/*connect Navigation, ViewModel, repository and screen*/
@Composable
fun RecipeDetailRoute(recipeId: String, onBack: () -> Unit) {
    val context = LocalContext.current
    val application = context.applicationContext as SmartPantryApplication
    val recipeRepository = application.recipeRepository
    val pantryRepository = application.pantryRepository
    val recipeDetailsViewModel: RecipeDetailViewModel = viewModel(
        key = "recipe_detail$recipeId",  // different recipe IDs should get different detail viewModel instances.
        factory = RecipeDetailViewModelFactory(
            recipeId = recipeId,
            recipeRepository = recipeRepository,
            pantryRepository = pantryRepository
        )
    )
    val uiState by recipeDetailsViewModel.uiState.collectAsStateWithLifecycle()

    RecipeDetailScreen(uiState = uiState, onBack = onBack)
}