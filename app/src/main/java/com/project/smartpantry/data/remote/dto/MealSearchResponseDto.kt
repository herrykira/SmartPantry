package com.project.smartpantry.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MealSearchResponseDto(val meals: List<MealDto>? = null)
