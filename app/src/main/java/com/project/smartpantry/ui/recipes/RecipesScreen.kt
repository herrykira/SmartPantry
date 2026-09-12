package com.project.smartpantry.ui.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.smartpantry.ui.theme.SmartPantryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(
    uiState: RecipeUiState,
    onSearchQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search recipes") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch() }) // Let the user press the keyboard's Search button instead of always tapping our button.
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = onSearch,
            enabled = uiState.searchQuery.isNotBlank() && uiState.searchState !is RecipesSearchState.Loading
        ) {
            Text("Search")
        }

        Spacer(Modifier.height(16.dp))

        when (val searchState = uiState.searchState) {
            is RecipesSearchState.Idle -> {
                Text(text = "Search for a recipe to get started")
            }

            is RecipesSearchState.Loading -> {
                CircularProgressIndicator()
            }

            is RecipesSearchState.Error -> {
                Text(text = searchState.message)
            }

            is RecipesSearchState.Success -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(items = searchState.recipes, key = { it.id }) { recipe ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = recipe.name,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                recipe.category?.let { Text(it) }
                                recipe.area?.let { Text(it) }
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
private fun RecipesScreenPreview() {
    SmartPantryTheme {
        RecipesScreen(uiState = RecipeUiState(), onSearchQueryChange = {}, onSearch = {})
    }
}