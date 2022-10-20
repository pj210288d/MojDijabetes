package rs.etf.running.ui.elements.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rs.etf.running.LOG_TAG

@Composable
fun CaloriesScreen(modifier: Modifier = Modifier) {
    Log.d(LOG_TAG, "(re)composition")

    // https://developer.android.com/jetpack/compose/state
    var weight by remember { mutableStateOf("") }

    // https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary
    OutlinedTextField(
        label = { Text(text = "Tezina") },
        value = weight,
        onValueChange = { newWeightValue -> weight = newWeightValue },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
    )
}