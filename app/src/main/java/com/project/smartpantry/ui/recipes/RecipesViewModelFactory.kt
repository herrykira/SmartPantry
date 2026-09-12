package com.project.smartpantry.ui.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.smartpantry.data.repository.RecipeRepository

//Teach Android how to construct RecipesViewModel(repository)
class RecipesViewModelFactory(private val repository: RecipeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipesViewModel::class.java)) {
            return RecipesViewModel(repository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: \${modelClass.name}\"")
    }
}