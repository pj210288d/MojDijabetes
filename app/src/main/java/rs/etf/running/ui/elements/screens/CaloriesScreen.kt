package rs.etf.running.ui.elements.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CaloriesScreen(modifier: Modifier = Modifier) {
    // https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary
    OutlinedTextField(
        label = { Text(text = "Tezina") },
        value = "",
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
    )
}