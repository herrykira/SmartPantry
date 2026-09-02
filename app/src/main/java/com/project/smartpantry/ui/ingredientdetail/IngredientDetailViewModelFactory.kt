package com.project.smartpantry.ui.ingredientdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.smartpantry.data.repository.PantryRepository

class IngredientDetailViewModelFactory(
    private val ingredientId: Long,
    private val repository: PantryRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IngredientDetailViewModel(
                ingredientId = ingredientId,
                repository = repository
            ) as T
        }
        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}