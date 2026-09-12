package com.project.smartpantry.ui.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.smartpantry.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipesViewModel(private val repository: RecipeRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(RecipeUiState())

    val uiState = _uiState.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _uiState.update {
            it.copy(searchQuery = query)
        }
    }

    fun searchRecipes() {
        val query = _uiState.value.searchQuery.trim()
        if (query.isBlank()) {
            return
        }
        viewModelScope.launch {  //don't want to block the main thread, and the coroutine should automatically be cancelled when the viewModel is destroyed
            _uiState.update {
                it.copy(
                    searchState = RecipesSearchState.Loading
                )
            }
            try {
                val recipes = repository.searchRecipes(query = query)
                _uiState.update {
                    it.copy(searchState = RecipesSearchState.Success(recipes))
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        searchState = RecipesSearchState.Error(
                            exception.message ?: "Unable to load recipes"
                        )
                    )
                }
            }
        }
    }
}