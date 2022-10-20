package rs.etf.running.ui.elements.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CaloriesScreen(modifier: Modifier = Modifier) {
    Greeting("Android")
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}