package com.project.smartpantry.ui.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.project.smartpantry.model.Recipe
import com.project.smartpantry.ui.theme.SmartPantryTheme

@Composable
fun RecipeCard(recipe: Recipe, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column {
            if (recipe.thumbnailUrl != null) {
                // Coil automatically uses caching, so already-loaded images generally don't need to be fully downloaded and decoded from scratch every time.
                // Coil includes memory and disk caching as part of its image-loading system
                // Compose’s normal Image cannot directly load an https://... image. Coil downloads, decodes, caches, and displays remote images for us
                AsyncImage(
                    model = recipe.thumbnailUrl,
                    contentDescription = "${recipe.name} recipe",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No image available",
                        style =
                            MaterialTheme
                                .typography
                                .bodySmall
                    )
                }
            }
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = recipe.name, style = MaterialTheme.typography.titleMedium)
                recipe.category?.let { category ->
                    Text(text = category, style = MaterialTheme.typography.bodyMedium)
                }
                recipe.area?.let { area ->
                    Text(text = area, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Recipe Card")
@Composable
private fun RecipeCardPreview() {
    SmartPantryTheme {
        RecipeCard(
            recipe = Recipe(
                id = "1",
                name = "Spicy Food",
                category = "Food",
                area = "Chinese",
                thumbnailUrl = null,
                instructions = null
            )
        )
    }
}