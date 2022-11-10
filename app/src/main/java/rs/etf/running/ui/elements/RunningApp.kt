package rs.etf.running.ui.elements

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import rs.etf.running.R
import rs.etf.running.TempCaloriesActivity
import rs.etf.running.TempRouteBrowseActivity
import rs.etf.running.topLevelRunningDestinations

@Composable
fun RunningApp(windowSizeClass: WindowSizeClass) {
    val isWidthCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.calories_toolbar_title)) },
                )
            },
            bottomBar = {
                BottomNavigation(backgroundColor = MaterialTheme.colors.surface) {
                    topLevelRunningDestinations.forEach {
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    imageVector = it.navigationIcon,
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = stringResource(id = it.navigationLabelResId)) },
                            selected = false,
                            selectedContentColor = MaterialTheme.colors.primary,
                            unselectedContentColor = Color.Gray,
                            onClick = { /*TODO*/ }
                        )
                    }
                }
            }
        ) { padding ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        val explicitIntent = Intent(context, TempRouteBrowseActivity::class.java)
                        ContextCompat.startActivity(context, explicitIntent, null)
                    },
                    modifier = Modifier
                        .width(240.dp)
                        .padding(32.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(text = stringResource(id = R.string.main_button_text_browse))
                }
                Button(
                    onClick = {
                        val explicitIntent = Intent(context, TempCaloriesActivity::class.java)
                        ContextCompat.startActivity(context, explicitIntent, null)
                    },
                    modifier = Modifier
                        .width(240.dp)
                        .padding(32.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(text = stringResource(id = R.string.main_button_text_calories))
                }
            }
        }
    }
}