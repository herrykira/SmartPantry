package com.project.smartpantry.ui.pantry

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.smartpantry.SmartPantryApplication
import com.project.smartpantry.model.Ingredient
import com.project.smartpantry.ui.theme.SmartPantryTheme

@Composable
fun PantryRoute(onRecipesClick: () -> Unit) {
    val context = LocalContext.current

    val application =
        context.applicationContext as SmartPantryApplication

    val viewModel: PantryViewModel = viewModel(
        factory = PantryViewModelFactory(
            repository = application.pantryRepository
        )
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle() //ViewModel exposes StateFlow, but Compose renders using Compose State
    PantryScreen(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onNameChange = viewModel::onNameChange,
        onQuantityChange = viewModel::onQuantityChange,
        onUnitChange = viewModel::onUnitChange,
        onCategoryChange = viewModel::onCategoryChange,
        onExpirationDateChange = viewModel::onExpirationDateChange,
        onSaveIngredient = viewModel::saveIngredient,
        onEditIngredient = viewModel::startEditingIngredient,
        onDeleteIngredient = viewModel::deleteIngredient,
        onResetForm = viewModel::resetForm,
        onRecipesClick = onRecipesClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantryScreen(
    uiState: PantryUiState,
    onSearchQueryChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onUnitChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onExpirationDateChange: (Long?) -> Unit,
    onSaveIngredient: () -> Unit,
    onEditIngredient: (Ingredient) -> Unit,
    onDeleteIngredient: (Long) -> Unit,
    onResetForm: () -> Unit,
    onRecipesClick: () -> Unit
) {
    var showAddDialog by rememberSaveable { mutableStateOf(false) }

    var categoryExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("My Pantry")
                },
                actions = {
                    TextButton(onClick = onRecipesClick) {
                        Text("Recipes")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onResetForm()
                showAddDialog = true
            }) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    ),
                label = { Text("Search pantry") },
                singleLine = true
            )

            if (uiState.ingredients.isEmpty() && uiState.searchQuery.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No ingredients found for \"${uiState.searchQuery}\"")
                }
            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 4.dp,
                        bottom = 88.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = uiState.ingredients,
                        key = { ingredient -> ingredient.id }) { ingredient -> // gives each item a stable identity, which becomes important when we later insert, delete, reorder, or animate items.
                        IngredientCard(
                            ingredient = ingredient,
                            onClick = {
                                onEditIngredient(ingredient)
                                showAddDialog = true
                                Log.d("PantryScreen", "Clicked: ${ingredient.name}")
                            },
                            onDelete = { onDeleteIngredient(ingredient.id) }
                        )
                    }
                }
            }
        }

    }

    if (showAddDialog) {
        AddIngredientDialog(
            name = uiState.name,
            onNameChange = onNameChange,

            quantity = uiState.quantity,
            onQuantityChange = onQuantityChange,

            showQuantityError = uiState.showQuantityError,

            unit = uiState.unit,
            onUnitChange = onUnitChange,

            category = uiState.category,
            onCategoryChange = onCategoryChange,

            expirationDateEpochDay = uiState.expirationDateEpochDay,
            onExpirationDateChange = onExpirationDateChange,

            categoryExpanded = categoryExpanded,
            onCategoryExpandedChange = { categoryExpanded = it },

            isEditing = uiState.isEditing,
            canSave = uiState.canSave,

            onDismiss = {
                onResetForm()
                showAddDialog = false
                categoryExpanded = false
            },
            onSave = {
                onSaveIngredient()
                showAddDialog = false
                categoryExpanded = false
            })
    }
}

@Preview(showBackground = true)
@Composable
private fun PantryScreenPreview() {
    SmartPantryTheme {
        PantryScreen(
            uiState = PantryUiState(
                ingredients = listOf(
                    Ingredient(
                        id = 1,
                        name = "Eggs",
                        quantity = 8,
                        unit = "pcs",
                        category = "Dairy & Eggs"
                    ),
                    Ingredient(
                        id = 2,
                        name = "Tomatoes",
                        quantity = 4,
                        unit = "pcs",
                        category = "Vegetables"
                    )
                )
            ),
            onSearchQueryChange = {},
            onNameChange = {},
            onQuantityChange = {},
            onUnitChange = {},
            onCategoryChange = {},
            onExpirationDateChange = {},
            onSaveIngredient = {},
            onEditIngredient = {},
            onDeleteIngredient = {},
            onResetForm = {},
            onRecipesClick = {},
            )
    }
}