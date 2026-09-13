package com.project.smartpantry.ui.recipes.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.smartpantry.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val recipeId: String,
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<RecipeDetailsState>(RecipeDetailsState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadRecipe()
    }

    private fun loadRecipe() {
        viewModelScope.launch {
            _uiState.value = RecipeDetailsState.Loading
            try {
                val recipe = recipeRepository.getRecipe(id = recipeId)
                _uiState.value = if (recipe == null) {
                    RecipeDetailsState.NotFound
                } else {
                    RecipeDetailsState.Success(recipe = recipe)
                }
            } catch (exception: Exception) {
                _uiState.value = RecipeDetailsState.Error(
                    message = exception.message
                        ?: "Unable to load recipe"
                )
            }
        }
    }
}