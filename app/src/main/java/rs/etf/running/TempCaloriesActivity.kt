package rs.etf.running

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import rs.etf.running.ui.elements.screens.CaloriesScreen
import rs.etf.running.ui.elements.theme.RunningApplicationTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class TempCaloriesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(activity = this)
            val isWidthCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
            RunningApplicationTheme {
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
                        CaloriesScreen(isWidthCompact, modifier = Modifier.padding(padding))
                    }
                }
            }
        }
    }
}