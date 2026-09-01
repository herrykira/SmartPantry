package com.project.smartpantry.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.project.smartpantry.data.local.entity.IngredientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Query(
        """
            SELECT *
            FROM ingredients
            ORDER BY name ASC
            """
    )
    fun observeIngredients(): Flow<List<IngredientEntity>> // data change propagates automatically

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredientEntity: IngredientEntity): Long

    @Query(
        """
        DELETE FROM ingredients
        WHERE id = :id
        """
    )
    suspend fun deleteIngredient(id: Long)

    @Update
    suspend fun updateIngredient(ingredient: IngredientEntity)
}