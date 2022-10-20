package rs.etf.running.ui.elements

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import rs.etf.running.ui.elements.screens.CaloriesScreen
import rs.etf.running.R

@Composable
fun RunningApp() {
    // A surface container using the 'background' color from the theme
    // https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
    ) {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.calories_toolbar_title)) },
            )
        }) { padding ->
            CaloriesScreen(modifier = Modifier.padding(padding))
        }
    }
}