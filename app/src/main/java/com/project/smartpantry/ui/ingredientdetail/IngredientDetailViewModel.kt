package com.project.smartpantry.ui.ingredientdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.smartpantry.data.repository.PantryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = IngredientDetailViewModel.Factory::class)
class IngredientDetailViewModel @AssistedInject constructor(
    @Assisted
    ingredientId: Long,
    repository: PantryRepository
) :
    ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(ingredientId: Long): IngredientDetailViewModel
    }

    val uiState = repository.observeIngredient(id = ingredientId)
        .map { ingredient -> IngredientDetailUiState(ingredient = ingredient, isLoading = false) }
        .stateIn(  // converts Flow -> stateFlow
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),   //keep upstream observation active while the UI is subscribed, and don't immediately tear it down during a very short temporary unsubscribe
            initialValue = IngredientDetailUiState()
        )
}