package com.project.smartpantry.data.remote.api

import com.project.smartpantry.data.remote.dto.MealSearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    /*
    * add the recipe-search API call*/
    @GET("search.php")
    suspend fun searchMeals(
        @Query("s")
        query: String
    ): MealSearchResponseDto

    /*
    * add the recipe-detail API call*/
    @GET("lookup.php")
    suspend fun getMealById(
        @Query("i")
        id: String
    ): MealSearchResponseDto
}