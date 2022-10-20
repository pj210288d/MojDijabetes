package rs.etf.running.ui.elements

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import rs.etf.running.ui.elements.screens.CaloriesScreen
import rs.etf.running.R

@Composable
fun RunningApp(windowSizeClass: WindowSizeClass) {
    // https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes
    val isWidthCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
    ) {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.calories_toolbar_title)) },
            )
        }) { padding ->
            CaloriesScreen(
                isWidthCompact = isWidthCompact,
                modifier = Modifier.padding(padding),
            )
        }
    }
}