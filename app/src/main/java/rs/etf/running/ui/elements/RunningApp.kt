package rs.etf.running.ui.elements

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import rs.etf.running.ui.elements.screens.CaloriesScreen

@Composable
fun RunningApp() {
    // A surface container using the 'background' color from the theme
    // https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        CaloriesScreen()
    }
}