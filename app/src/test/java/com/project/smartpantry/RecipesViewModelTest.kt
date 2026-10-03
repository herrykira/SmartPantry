package com.project.smartpantry

import com.project.smartpantry.data.remote.api.MealApiService
import com.project.smartpantry.data.remote.dto.MealDto
import com.project.smartpantry.data.remote.dto.MealSearchResponseDto
import com.project.smartpantry.data.repository.RecipeRepository
import com.project.smartpantry.ui.recipes.RecipesSearchState
import com.project.smartpantry.ui.recipes.RecipesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecipesViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `search recipes returns success`() =
        runTest {
            val api =
                FakeMealApiService(
                    response =
                        MealSearchResponseDto(
                            meals = listOf(
                                MealDto(
                                    id = "123",
                                    name =
                                        "Chicken Curry",
                                    category =
                                        "Chicken",
                                    area =
                                        "Indian"
                                )
                            )
                        )
                )
            val repository = RecipeRepository(apiService = api)

            val viewModel = RecipesViewModel(repository = repository)

            viewModel.onSearchQueryChange("chicken")
            viewModel.searchRecipes()
            advanceUntilIdle() // run all coroutine work waiting in the test scheduler
            val state = viewModel.uiState.value

            assertEquals("chicken", state.searchQuery)
            assertTrue(state.searchState is RecipesSearchState.Success)

            val success =
                state.searchState
                        as RecipesSearchState.Success

            assertEquals(
                1,
                success.recipes.size
            )

            assertEquals(
                "Chicken Curry",
                success.recipes[0].name
            )
        }

    @Test
    fun `search recipes returns error when api fails`() =
        runTest {
            val api = FakeMealApiService(error = java.io.IOException("No internet"))

            val repository = RecipeRepository(apiService = api)

            val viewModel = RecipesViewModel(repository = repository)

            viewModel.onSearchQueryChange("chicken")
            viewModel.searchRecipes()
            advanceUntilIdle()
            val state =
                viewModel.uiState.value

            assertTrue(
                state.searchState
                        is RecipesSearchState.Error
            )

            val error =
                state.searchState
                        as RecipesSearchState.Error

            assertEquals(
                "No internet",
                error.message
            )
        }

    @Test
    fun `blank query does not start search`() =
        runTest {

            val api =
                FakeMealApiService()

            val repository =
                RecipeRepository(
                    apiService = api
                )

            val viewModel =
                RecipesViewModel(
                    repository = repository
                )

            viewModel.onSearchQueryChange(
                "   "
            )

            viewModel.searchRecipes()

            advanceUntilIdle()

            assertTrue(
                viewModel.uiState.value.searchState
                        is RecipesSearchState.Idle
            )
        }

    @Test
    fun `search shows loading before result`() =
        runTest {
            val api = FakeMealApiService(
                delayMillis = 1_000, response = MealSearchResponseDto(
                    meals = listOf(
                        MealDto(id = "1", name = "chicken")
                    )
                )
            )

            val repository =
                RecipeRepository(
                    apiService = api
                )

            val viewModel =
                RecipesViewModel(
                    repository = repository
                )

            viewModel.onSearchQueryChange(
                "chicken"
            )

            viewModel.searchRecipes()

            runCurrent()
            assertTrue(viewModel.uiState.value.searchState is RecipesSearchState.Loading)

            advanceTimeBy(1_000)
            advanceUntilIdle()
            assertTrue(viewModel.uiState.value.searchState is RecipesSearchState.Success)
        }
}

private class FakeMealApiService(
    private val response: MealSearchResponseDto = MealSearchResponseDto(meals = emptyList()),
    private val error: Throwable? = null,
    private val delayMillis: Long = 0
) :
    MealApiService {
    override suspend fun searchMeals(query: String): MealSearchResponseDto {
        if (delayMillis > 0) {
            kotlinx.coroutines.delay(delayMillis)
        }
        error?.let { throw it }
        return response
    }

    override suspend fun getMealById(id: String): MealSearchResponseDto {
        return MealSearchResponseDto(meals = null)
    }
}