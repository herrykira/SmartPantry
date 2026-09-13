package com.project.smartpantry.ui.recipes.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.smartpantry.data.repository.PantryRepository
import com.project.smartpantry.data.repository.RecipeRepository
import com.project.smartpantry.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val recipeId: String,
    private val recipeRepository: RecipeRepository,
    private val pantryRepository: PantryRepository
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
                if (recipe == null) {
                    _uiState.value = RecipeDetailsState.NotFound
                    return@launch
                }
                observePantry(recipe = recipe) // instead of immediate creating success, we need to combine the recipe with Room data.
            } catch (exception: Exception) {
                _uiState.value = RecipeDetailsState.Error(
                    message = exception.message
                        ?: "Unable to load recipe"
                )
            }
        }
    }

    private suspend fun observePantry(recipe: Recipe) {
        pantryRepository.observeAllIngredients().collect { pantryIngredients ->
            val availability = recipe.ingredients.map { recipeIngredient ->
                RecipeIngredientAvailability(
                    ingredient = recipeIngredient,
                    isInPantry = pantryIngredients.any { pantryIngredient ->
                        pantryIngredient.name.trim()
                            .equals(recipeIngredient.name.trim(), ignoreCase = true)
                    })
            }
            _uiState.value = RecipeDetailsState.Success(recipe = recipe, ingredients = availability)
        }
    }
}