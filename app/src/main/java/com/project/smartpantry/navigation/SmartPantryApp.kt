package com.project.smartpantry.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.project.smartpantry.ui.IngredientDetailRoute
import com.project.smartpantry.ui.pantry.PantryRoute
import com.project.smartpantry.ui.recipes.RecipesScreen

@Composable
fun SmartPantryApp() {
    // save and restore serializable navigation keys across configuration changes and Android process recreation
    val backStack = rememberNavBackStack(PantryDestination)

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