package rs.etf.running.ui.elements

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import rs.etf.running.ui.elements.screens.CaloriesScreen
import rs.etf.running.R
import rs.etf.running.ui.elements.screens.RouteBrowseScreen

@Composable
fun RunningApp(windowSizeClass: WindowSizeClass) {
    val isWidthCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    var currentScreen by rememberSaveable { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.calories_toolbar_title)) },
                )
            }
        ) { padding ->
            // !!! BackStack is not affected !!!
            when (currentScreen) {
                "routeBrowse" -> RouteBrowseScreen(modifier = Modifier.padding(padding))
                "calories" -> CaloriesScreen(isWidthCompact, modifier = Modifier.padding(padding))
                else -> Column(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { currentScreen = "routeBrowse" },
                        modifier = Modifier
                            .width(240.dp)
                            .padding(32.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = stringResource(id = R.string.main_button_text_browse))
                    }
                    Button(
                        onClick = { currentScreen = "calories" },
                        modifier = Modifier
                            .width(240.dp)
                            .padding(32.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = stringResource(id = R.string.main_button_text_calories))
                    }
                }
            }
        }
    }
}