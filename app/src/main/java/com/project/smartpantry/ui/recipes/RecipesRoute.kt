package com.project.smartpantry.ui.recipes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.smartpantry.SmartPantryApplication

/*
* connect RecipesViewModel to RecipesScreen*/
@Composable
fun RecipeRoute(onRecipeClick: (String) -> Unit) {

    val viewModel: RecipesViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RecipesScreen(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onSearch = viewModel::searchRecipes,
        onRecipeClick = onRecipeClick
    )
}