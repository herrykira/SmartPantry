package com.project.smartpantry.ui.pantry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.smartpantry.ui.theme.SmartPantryTheme
import com.project.smartpantry.util.epochDayToUtcMills
import com.project.smartpantry.util.utcMillsToEpochDay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// State hoisting: doesn't contain variable but populate it
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientDialog(
    name: String,
    onNameChange: (String) -> Unit,

    quantity: String,
    onQuantityChange: (String) -> Unit,

    showQuantityError: Boolean,

    unit: String,
    onUnitChange: (String) -> Unit,

    category: String,
    onCategoryChange: (String) -> Unit,

    categoryExpanded: Boolean,
    onCategoryExpandedChange: (Boolean) -> Unit,

    expirationDateEpochDay: Long?,
    onExpirationDateChange: (Long?) -> Unit,

    isEditing: Boolean,
    canSave: Boolean,

    onDismiss: () -> Unit,
    onSave: () -> Unit,

    modifier: Modifier = Modifier
) {
    val categories = listOf(
        "Vegetables",
        "Fruits",
        "Meat",
        "Dairy & Eggs",
        "Grains",
        "Seafood",
        "Condiments",
        "Other"
    )

    var showDatePicker by rememberSaveable {
        mutableStateOf(false)
    }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
            Text(
                if (isEditing) {
                    "Edit Ingredient"
                } else {
                    "Add Ingredient"
                }
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Name") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = quantity,
                    onValueChange = onQuantityChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Quantity") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = showQuantityError,
                    supportingText = {
                        if (showQuantityError) {
                            Text("Quantity must be greater than 0")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))


                OutlinedTextField(
                    value = unit,
                    onValueChange = onUnitChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Unit")
                    },
                    placeholder = {
                        Text("pcs, lb, oz...")
                    },
                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { onCategoryExpandedChange(it) }
                ) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = {},
                        modifier = Modifier
                            .menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = true)
                            .fillMaxWidth(),
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded)
                        })

                    DropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { onCategoryExpandedChange(false) }
                    ) {
                        categories.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    onCategoryChange(item)
                                    onCategoryExpandedChange(false)
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                val expirationText = expirationDateEpochDay?.let { epochDay ->
                    LocalDate.ofEpochDay(epochDay)
                        .format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
                } ?: "No expiration date"

                Text(text = "Expiration: $expirationText")

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = { showDatePicker = true }) {
                        Text(
                            if (expirationDateEpochDay == null) {
                                "Choose date"
                            } else {
                                "Change date"
                            }
                        )
                    }
                    if (expirationDateEpochDay != null) {
                        TextButton(onClick = { onExpirationDateChange(null) }) {
                            Text("Clear")
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onSave, enabled = canSave) {
                Text(
                    if (isEditing) "Update" else "Add"
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )

    if (showDatePicker) {
        val datePickerState =
            rememberDatePickerState(initialSelectedDateMillis = expirationDateEpochDay?.let {
                epochDayToUtcMills(it)
            })

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        onExpirationDateChange(
                            utcMillsToEpochDay(millis = millis)
                        )
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Add Ingredient Dialog"
)
@Composable
private fun AddIngredientDialogPreview() {
    SmartPantryTheme {
        AddIngredientDialog(
            name = "Tomatoes",
            onNameChange = {},
            quantity = "4",
            onQuantityChange = {},
            showQuantityError = false,
            unit = "pcs",
            onUnitChange = {},
            category = "Vegetables",
            onCategoryChange = {},
            categoryExpanded = false,
            expirationDateEpochDay = LocalDate.of(2026, 9, 5).toEpochDay(),
            onExpirationDateChange = {},
            onCategoryExpandedChange = {},
            isEditing = true,
            canSave = true,
            onDismiss = {},
            onSave = {}
        )
    }
}