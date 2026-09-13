package com.project.smartpantry.ui.recipes.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

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