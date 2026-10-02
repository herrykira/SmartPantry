package com.project.smartpantry.ui.ingredientdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun IngredientDetailRoute(ingredientId: Long, onBack: () -> Unit) {

    val viewModel: IngredientDetailViewModel =
        hiltViewModel<IngredientDetailViewModel, IngredientDetailViewModel.Factory>(
            creationCallback = { factory ->
                factory.create(ingredientId = ingredientId)
            }
        )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    IngredientDetailScreen(uiState = uiState, onBack = onBack)
}