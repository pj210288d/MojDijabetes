package rs.etf.running.ui.elements.screens

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CaloriesScreen(modifier: Modifier = Modifier) {
    // https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary
    OutlinedTextField(
        label = { Text(text = "Tezina") },
        value = "",
        onValueChange = {},
    )
}