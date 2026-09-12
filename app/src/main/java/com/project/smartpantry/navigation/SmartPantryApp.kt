package com.project.smartpantry.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.project.smartpantry.ui.IngredientDetailRoute
import com.project.smartpantry.ui.pantry.PantryRoute
import com.project.smartpantry.ui.recipes.RecipesScreen

@Composable
fun SmartPantryApp() {
    // save and restore serializable navigation keys across configuration changes and Android process recreation
    val pantryBackStack = rememberNavBackStack(PantryDestination)
    val receiptsBackStack = rememberNavBackStack(RecipesDestination)
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val currentBackStack = if (selectedTab == 0) {
        pantryBackStack
    } else {
        receiptsBackStack
    }

    BackHandler(enabled = selectedTab != 0 && currentBackStack.size == 1) {
        selectedTab = 0
    }
    Scaffold(bottomBar = {
        NavigationBar {
            NavigationBarItem(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Pantry") },
                label = { Text("Pantry") })
            NavigationBarItem(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                icon = { Icon(imageVector = Icons.Default.List, contentDescription = "Recipes") })
        }
    }) { innerPadding ->
        NavDisplay(
            backStack = currentBackStack,
            modifier = Modifier.padding(innerPadding),
            onBack = { currentBackStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<PantryDestination> {
                    PantryRoute(
                        onIngredientClick = { ingredientId ->
                            pantryBackStack.add(
                                IngredientDetailDestination(
                                    ingredientId = ingredientId
                                )
                            )
                        }
                    )
                }
                entry<RecipesDestination> {
                    RecipesScreen()
                }

                entry<IngredientDetailDestination> { destination ->
                    IngredientDetailRoute(
                        ingredientId = destination.ingredientId,
                        onBack = { pantryBackStack.removeLastOrNull() })
                }
            })
    }
}