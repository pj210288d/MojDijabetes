package rs.etf.running.ui.elements

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import rs.etf.running.*
import rs.etf.running.ui.elements.screens.CaloriesScreen
import rs.etf.running.ui.elements.screens.RouteBrowseScreen
import rs.etf.running.ui.elements.screens.RouteDetailsScreen
import rs.etf.running.ui.elements.screens.WorkoutListScreen

@Composable
fun RunningApp(windowSizeClass: WindowSizeClass) {
    val isWidthCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: RouteBrowse.route

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.surface,
            ) {
                topLevelRunningDestinations.forEach {
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = it.navigationIcon,
                                contentDescription = null
                            )
                        },
                        label = { Text(text = stringResource(id = it.navigationLabelResId)) },
                        selected = currentRoute.startsWith(it.route),
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = Color.Gray,
                        onClick = {
                            navController.navigate(it.route) {
                                launchSingleTop = true
                                // https://developer.android.com/guide/navigation/multi-back-stacks#navoptions
                                restoreState = true
                                popUpTo(RouteBrowse.route) {
                                    saveState = true
                                }
                            }
                        },
                    )
                }
            }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = RouteBrowse.route,
            modifier = Modifier.padding(padding),
        ) {
            composable(route = RouteBrowse.route) {
                RouteBrowseScreen(
                    onClickRoute = { routeIndex ->
                        navController.navigate(
                            RouteDetails.route.replace(
                                "{index}", routeIndex.toString()
                            )
                        )
                    },
                )
            }
            composable(
                route = RouteDetails.route,
                arguments = RouteDetails.routeArguments,
            ) {
                val routeIndex = it.arguments?.getInt(RouteDetails.routeArguments.first().name) ?: 0
                RouteDetailsScreen(
                    routeIndex = routeIndex,
                    onNavigateUp = { navController.navigateUp() }
                )
            }
            composable(route = WorkoutList.route) {
                WorkoutListScreen()
            }
            composable(route = Calories.route) {
                CaloriesScreen(isWidthCompact = isWidthCompact)
            }
        }
    }
}