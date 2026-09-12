package com.project.smartpantry.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealDto(
    @SerialName("idMeal")
    val id: String,
    @SerialName("strMeal")
    val name: String,
    @SerialName("strCategory")
    val category: String? = null,
    @SerialName("strArea")
    val area: String? = null,
    @SerialName("strMealThumb")
    val thumbnailUrl: String? = null,
    @SerialName("strInstructions")
    val instructions: String? = null
)