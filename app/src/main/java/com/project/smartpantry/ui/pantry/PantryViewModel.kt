package com.project.smartpantry.ui.pantry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.smartpantry.data.repository.PantryRepository
import com.project.smartpantry.model.Ingredient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class PantryViewModel(private val repository: PantryRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(
        PantryUiState()
    )

    val uiState: StateFlow<PantryUiState> = _uiState.asStateFlow()

    init {
        observeIngredients()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeIngredients() {
        viewModelScope.launch {
            _uiState
                .map { state ->
                    state.searchQuery
                }
                .debounce(300.milliseconds) // wait until the user pauses for about 300ms to avoid multiple unnecessary search
                .distinctUntilChanged()  //avoid unnecessary search, if the searchQuery is the same
                .flatMapLatest { query ->  // stop previous flow and switch to latest flow
                    repository.observeIngredients(query = query)
                }
                .collect { ingredients ->
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