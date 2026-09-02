package com.project.smartpantry.navigation

sealed interface AppDestination

data object PantryDestination: AppDestination
data object RecipesDestination: AppDestination