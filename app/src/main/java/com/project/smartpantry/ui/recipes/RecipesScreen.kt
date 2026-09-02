package com.project.smartpantry.ui.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.smartpantry.ui.theme.SmartPantryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(onBack: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Recipes") }
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Recipes will be added next.")

            Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
                Text("Back to Pantry")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun RecipesScreenPreview() {
    SmartPantryTheme {
        RecipesScreen(onBack = {})
    }
}