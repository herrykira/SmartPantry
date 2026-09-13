package com.project.smartpantry.ui.recipes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.smartpantry.SmartPantryApplication

/*
* connect RecipesViewModel to RecipesScreen*/
@Composable
fun RecipeRoute(onRecipeClick: (String) -> Unit) {
    val context = LocalContext.current
    val application = context.applicationContext as SmartPantryApplication
    val viewModel: RecipesViewModel =
        viewModel(factory = RecipesViewModelFactory(repository = application.recipeRepository))

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RecipesScreen(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onSearch = viewModel::searchRecipes,
        onRecipeClick = onRecipeClick
    )
}