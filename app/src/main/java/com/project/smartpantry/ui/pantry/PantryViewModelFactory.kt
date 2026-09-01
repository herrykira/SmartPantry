package com.project.smartpantry.ui.pantry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.smartpantry.data.repository.PantryRepository

class PantryViewModelFactory(
    private val repository: PantryRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                PantryViewModel::class.java
            )
        ) {

            @Suppress("UNCHECKED_CAST")
            return PantryViewModel(
                repository = repository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}