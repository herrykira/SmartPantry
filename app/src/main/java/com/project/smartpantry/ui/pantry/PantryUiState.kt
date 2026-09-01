package com.project.smartpantry.ui.pantry

import com.project.smartpantry.model.Ingredient

data class PantryUiState(
    val ingredients: List<Ingredient> = emptyList(),
    val name: String = "",
    val quantity: String = "",
    val unit: String = "",
    val category: String = "",
    val searchQuery: String = "",
    val editingIngredientId: Long? = null,

    val expirationDateEpochDay: Long? = null
) {
    val quantityValue: Int?
        get() = quantity.toIntOrNull()

    val isQuantityValid: Boolean
        get() = quantityValue != null && quantityValue!! > 0

    val showQuantityError: Boolean
        get() = quantity.isNotEmpty() && !isQuantityValid

    val canSave: Boolean
        get() = name.isNotBlank() && isQuantityValid && unit.isNotBlank() && category.isNotBlank()

    val filteredIngredients: List<Ingredient>
        get() {
            if (searchQuery.isBlank()) {
                return ingredients
            }
            return ingredients.filter { ingredient ->
                ingredient.name.contains(
                    searchQuery,
                    ignoreCase = true
                ) || ingredient.category.contains(searchQuery, ignoreCase = true)
            }
        }

    val isEditing: Boolean
        get() = editingIngredientId != null
}
