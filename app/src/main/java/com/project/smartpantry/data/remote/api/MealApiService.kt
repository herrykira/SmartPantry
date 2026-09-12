package com.project.smartpantry.data.remote.api

import com.project.smartpantry.data.remote.dto.MealSearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("search.php")
    suspend fun searchMeals(
        @Query("s")
        query: String
    ): MealSearchResponseDto
}