package com.project.smartpantry.ui.recipes.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.smartpantry.data.repository.RecipeRepository

class RecipeDetailViewModelFactory(
    private val recipeId: String,
    private val recipeRepository: RecipeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeDetailViewModel::class.java)) {
            return RecipeDetailViewModel(
                recipeId = recipeId,
                recipeRepository = recipeRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
