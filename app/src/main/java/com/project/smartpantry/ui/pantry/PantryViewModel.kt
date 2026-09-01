package com.project.smartpantry.ui.pantry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.smartpantry.data.repository.PantryRepository
import com.project.smartpantry.model.Ingredient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PantryViewModel(private val repository: PantryRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(
        PantryUiState()
    )

    val uiState: StateFlow<PantryUiState> = _uiState.asStateFlow()

    init {
        observeIngredients()
    }

    private fun observeIngredients() {
        viewModelScope.launch {
            repository.ingredients.collect { ingredients ->
                _uiState.update { currentState ->
                    currentState.copy(ingredients = ingredients)
                }
            }
        }
    }

    fun onNameChange(name: String) {
        _uiState.update { currentState ->
            currentState.copy(name = name)
        }
    }

    fun onQuantityChange(quantity: String) {
        if (quantity.isEmpty() || quantity.all { it.isDigit() }) {
            _uiState.update { currentState ->
                currentState.copy(quantity = quantity)
            }
        }
    }

    fun onUnitChange(unit: String) {
        _uiState.update { currentState -> currentState.copy(unit = unit) }
    }

    fun onCategoryChange(category: String) {
        _uiState.update { currentState ->
            currentState.copy(category = category)
        }
    }

    fun saveIngredient() {
        val currentState = _uiState.value

        if (!currentState.canSave) {
            return
        }
        val quantityValue = currentState.quantityValue ?: return

        viewModelScope.launch {
            val editingId =
                currentState.editingIngredientId
            if (editingId != null) {
                //Edit
                val ingredient = Ingredient(
                    id = editingId,
                    name = currentState.name.trim(),
                    quantity = quantityValue,
                    unit = currentState.unit.trim(),
                    category = currentState.category,
                    expirationDateEpochDay = currentState.expirationDateEpochDay
                )
                repository.updateIngredient(ingredient = ingredient)
            } else {
                //Add
                repository.addIngredient(
                    name = currentState.name.trim(),
                    quantity = quantityValue,
                    unit = currentState.unit.trim(),
                    category = currentState.category,
                    expirationDateEpochDay = currentState.expirationDateEpochDay
                )
            }
            resetForm()
        }
        // we don't need to update _uiState, because the database does this for us:
        // repository.addIngredient() -> DAO INSERT -> Room database changes -> observeIngredients() emits -> viewModel receives new list -> _uiState changes
    }

    fun deleteIngredient(id: Long) {
        viewModelScope.launch {
            repository.deleteIngredient(id = id)
        }
    }

    fun resetForm() {
        _uiState.update { state ->
            state.copy(
                editingIngredientId = null,
                name = "",
                quantity = "",
                unit = "",
                category = "",
                expirationDateEpochDay = null,
            )
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { currentState ->
            currentState.copy(searchQuery = query)
        }
    }

    fun startEditingIngredient(ingredient: Ingredient) {
        _uiState.update {
            it.copy(
                editingIngredientId = ingredient.id,
                name = ingredient.name,
                quantity = ingredient.quantity.toString(),
                unit = ingredient.unit,
                category = ingredient.category,
                expirationDateEpochDay = ingredient.expirationDateEpochDay
            )
        }
    }

    fun onExpirationDateChange(epochDay: Long?) {
        _uiState.update {
            it.copy(expirationDateEpochDay = epochDay)
        }
    }

}