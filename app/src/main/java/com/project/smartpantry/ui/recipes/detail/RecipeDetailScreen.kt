package com.project.smartpantry.ui.recipes.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.project.smartpantry.model.Recipe
import com.project.smartpantry.model.RecipeIngredient
import com.project.smartpantry.ui.theme.SmartPantryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(uiState: RecipeDetailsState, onBack: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Recipe Details") },
            navigationIcon = { TextButton(onClick = onBack) { Text("Back") } })
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState) {
                RecipeDetailsState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                RecipeDetailsState.NotFound -> {
                    Text(text = "Recipe not found", modifier = Modifier.align(Alignment.Center))
                }

                is RecipeDetailsState.Error -> {
                    Text(
                        text = uiState.message, modifier = Modifier.align(Alignment.Center)
                    )
                }

                is RecipeDetailsState.Success -> {
                    val recipe = uiState.recipe

                    LazyColumn {
                        item {
                            recipe.thumbnailUrl?.let { imageUrl ->
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = recipe.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(16f / 9f),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        item {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = recipe.name,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                                recipe.category?.let {
                                    Text(text = "Category: $it")
                                }
                                recipe.area?.let {
                                    Text(text = "Cuisine: $it")
                                }
                                val availableCount = uiState.ingredients.count { it.isInPantry }
                                val totalCount = uiState.ingredients.size

                                Text(
                                    text = "$availableCount of $totalCount ingredients in pantry",
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                if (recipe.ingredients.isNotEmpty()) {
                                    Text(
                                        text = "Ingredients",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    uiState.ingredients.forEach { availability ->
                                        val ingredient = availability.ingredient
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(text = ingredient.name)
                                                if (ingredient.measure.isNotBlank()) {
                                                    Text(
                                                        text = ingredient.measure,
                                                        style = MaterialTheme.typography.bodySmall
                                                    )
                                                }
                                            }
                                            Text(
                                                text = if (availability.isInPantry) {
                                                    "✓ In pantry"
                                                } else {
                                                    "Missing"
                                                }
                                            )
                                        }
                                    }
                                }
                                recipe.instructions?.let {
                                    Text(
                                        text = "Instructions",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(text = it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeDetailScreenPreview() {
    SmartPantryTheme {
        RecipeDetailScreen(
            uiState = RecipeDetailsState.Success(
                recipe = Recipe(
                    id = "1",
                    name = "chicken",
                    category = "food",
                    area = "Chinese",
                    thumbnailUrl = null,
                    instructions = "",
                    ingredients = listOf(RecipeIngredient("egg", "500g"))
                ),
                ingredients = listOf(
                    RecipeIngredientAvailability(
                        RecipeIngredient("egg", "500g"),
                        isInPantry = true
                    )
                )
            ), onBack = {})
    }
}