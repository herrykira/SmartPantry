package com.project.smartpantry.data.repository

import com.project.smartpantry.data.local.dao.IngredientDao
import com.project.smartpantry.data.local.entity.IngredientEntity
import com.project.smartpantry.data.mapper.toIngredient
import com.project.smartpantry.data.mapper.toIngredientEntity
import com.project.smartpantry.model.Ingredient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//Hide Room from the viewModel
//Convert database models
class PantryRepository(private val ingredientDao: IngredientDao) {

    fun observeIngredients(query: String): Flow<List<Ingredient>> {
        val source = if (query.isBlank()) {
            ingredientDao.observeIngredients()
        } else {
            ingredientDao.searchIngredients(query = query.trim())
        }
        return source.map { entities -> entities.map { entity -> entity.toIngredient() } }
    }

    suspend fun addIngredient(
        name: String,
        quantity: Int,
        unit: String,
        category: String,
        expirationDateEpochDay: Long?
    ) {
        val entity =
            IngredientEntity(
                name = name,
                quantity = quantity,
                unit = unit,
                category = category,
                expirationDateEpochDay = expirationDateEpochDay
            )

        ingredientDao.insertIngredient(entity)
    }

    suspend fun deleteIngredient(id: Long) {
        ingredientDao.deleteIngredient(id = id)
    }

    suspend fun updateIngredient(ingredient: Ingredient) {
        ingredientDao.updateIngredient(ingredient = ingredient.toIngredientEntity())
    }
}