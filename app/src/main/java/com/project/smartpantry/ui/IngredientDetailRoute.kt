package com.project.smartpantry.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.smartpantry.SmartPantryApplication
import com.project.smartpantry.ui.ingredientdetail.IngredientDetailScreen
import com.project.smartpantry.ui.ingredientdetail.IngredientDetailViewModel
import com.project.smartpantry.ui.ingredientdetail.IngredientDetailViewModelFactory

@Composable
fun IngredientDetailRoute(ingredientId: Long, onBack: () -> Unit) {
    val context = LocalContext.current

    val application = context.applicationContext as SmartPantryApplication

    val viewModel: IngredientDetailViewModel = viewModel(
        factory = IngredientDetailViewModelFactory(
            ingredientId = ingredientId,
            repository = application.pantryRepository
        )
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    IngredientDetailScreen(uiState = uiState, onBack = onBack)
}