package com.project.smartpantry.ui.ingredientdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientDetailScreen(uiState: IngredientDetailUiState, onBack: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Ingredient Details") }, navigationIcon = {
            TextButton(onClick = onBack) {
                Text("Back")
            }
        })
    }) { innerPadding ->
        when {
            uiState.isLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp)
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.ingredient == null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp)
                ) {
                    Text("Ingredient not found.")
                }
            }

            else -> {
                val ingredient = uiState.ingredient
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = ingredient.name)

                    HorizontalDivider()

                    Text(text = "Quantity: ${ingredient.quantity} ${ingredient.unit}")

                    Text(
                        text =
                            "Category: ${ingredient.category}"
                    )

                    ingredient.expirationDateEpochDay?.let { epochDay ->
                        val date = LocalDate.ofEpochDay(epochDay)
                        val formattedDate = date.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
                        Text(text = "Expiration: $formattedDate")
                    }
                }
            }
        }

    }
}