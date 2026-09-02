package com.project.smartpantry.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.project.smartpantry.ui.IngredientDetailRoute
import com.project.smartpantry.ui.pantry.PantryRoute
import com.project.smartpantry.ui.recipes.RecipesScreen

@Composable
fun SmartPantryApp() {
    val backStack = remember { mutableStateListOf<AppDestination>(PantryDestination) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<PantryDestination> {
                PantryRoute(
                    onRecipesClick = { backStack.add(RecipesDestination) },
                    onIngredientClick = { ingredientId ->
                        backStack.add(
                            IngredientDetailDestination(
                                ingredientId = ingredientId
                            )
                        )
                    }
                )
            }
            entry<RecipesDestination> {
                RecipesScreen(onBack = { backStack.removeLastOrNull() })
            }

            entry<IngredientDetailDestination> { destination ->
                IngredientDetailRoute(
                    ingredientId = destination.ingredientId,
                    onBack = { backStack.removeLastOrNull() })
            }
        })
}