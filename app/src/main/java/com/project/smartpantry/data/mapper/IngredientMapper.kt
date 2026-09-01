package com.project.smartpantry.data.mapper

import com.project.smartpantry.data.local.entity.IngredientEntity
import com.project.smartpantry.model.Ingredient

// Room -> App
fun IngredientEntity.toIngredient(): Ingredient {
    return Ingredient(
        id = id,
        name = name,
        quantity = quantity,
        unit = unit,
        category = category,
        expirationDateEpochDay = expirationDateEpochDay
    )
}


// App -> Room
fun Ingredient.toIngredientEntity(): IngredientEntity {
    return IngredientEntity(
        id = id,
        name = name,
        quantity = quantity,
        unit = unit,
        category = category,
        expirationDateEpochDay = expirationDateEpochDay
    )
}