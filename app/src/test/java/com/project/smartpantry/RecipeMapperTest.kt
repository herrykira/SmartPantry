package com.project.smartpantry

import com.project.smartpantry.data.mapper.toRecipe
import com.project.smartpantry.data.remote.dto.MealDto
import org.junit.Assert.assertEquals
import org.junit.Test

class RecipeMapperTest {
    @Test
    fun `meal dto maps basic recipe fields`() {

        val dto =
            MealDto(
                id = "123",
                name = "Chicken Curry",
                category = "Chicken",
                area = "Indian",
                thumbnailUrl = "image-url",
                instructions = "Cook chicken"
            )

        val recipe =
            dto.toRecipe()

        assertEquals(
            "123",
            recipe.id
        )

        assertEquals(
            "Chicken Curry",
            recipe.name
        )

        assertEquals(
            "Chicken",
            recipe.category
        )

        assertEquals(
            "Indian",
            recipe.area
        )
    }

    @Test
    fun `meal ingredients map into recipe ingredient list`() {

        val dto =
            MealDto(
                id = "123",
                name = "Chicken Curry",

                ingredient1 = "Chicken",
                measure1 = "500g",

                ingredient2 = "Onion",
                measure2 = "1",

                ingredient3 = "",
                measure3 = ""
            )

        val recipe =
            dto.toRecipe()

        assertEquals(
            2,
            recipe.ingredients.size
        )

        assertEquals(
            "Chicken",
            recipe.ingredients[0].name
        )

        assertEquals(
            "500g",
            recipe.ingredients[0].measure
        )

        assertEquals(
            "Onion",
            recipe.ingredients[1].name
        )

        assertEquals(
            "1",
            recipe.ingredients[1].measure
        )
    }
}