package com.project.smartpantry.ui.pantry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.smartpantry.model.Ingredient
import com.project.smartpantry.ui.theme.SmartPantryTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun IngredientCard(
    ingredient: Ingredient,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = ingredient.name,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = "${ingredient.quantity} ${ingredient.unit}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = ingredient.category,
                    style = MaterialTheme.typography.bodySmall
                )

                val status = expirationStatus(ingredient.expirationDateEpochDay)

                ingredient.expirationDateEpochDay?.let { epochDay ->
                    Spacer(modifier = Modifier.height(6.dp))

                    val expirationDate = LocalDate.ofEpochDay(epochDay)

                    Text(
                        text = when (status) {
                            ExpirationStatus.EXPIRED ->
                                "Expired • ${
                                    expirationDate.format(
                                        DateTimeFormatter.ofPattern(
                                            "MMM d"
                                        )
                                    )
                                }"

                            ExpirationStatus.EXPIRING_SOON ->
                                "Expired soon • ${
                                    expirationDate.format(
                                        DateTimeFormatter.ofPattern(
                                            "MMM d"
                                        )
                                    )
                                }"

                            else ->
                                "Expires ${expirationDate.format(DateTimeFormatter.ofPattern("MMM d"))}"
                        },
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            TextButton(onClick = onDelete) {
                Text("Delete")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun IngredientCardPreview() {
    SmartPantryTheme {
        IngredientCard(
            ingredient = Ingredient(
                id = 1,
                name = "Eggs",
                quantity = 8,
                unit = "pcs",
                category = "Dairy & Eggs"
            ),
            onClick = {},
            onDelete = {}
        )
    }
}
