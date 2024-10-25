package rs.etf.running

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface RunningDestination {
    val topAppBarLabelResId: Int
    val route: String
}

interface TopLevelRunningDestination : RunningDestination {
    val navigationIcon: ImageVector
    val navigationLabelResId: Int
}

object RouteBrowse : TopLevelRunningDestination {
    override val navigationIcon = Icons.Default.Terrain
    override val navigationLabelResId = R.string.main_navigation_item_title_routes
    override val topAppBarLabelResId = R.string.route_browse_toolbar_title
    override val route = "routes"
}

object WorkoutList : TopLevelRunningDestination {
    override val navigationIcon = Icons.Default.BarChart
    override val navigationLabelResId = R.string.main_navigation_item_title_workouts
    override val topAppBarLabelResId = R.string.workout_list_toolbar_title
    override val route = "workouts"
}

object Calories : TopLevelRunningDestination {
    override val navigationIcon = Icons.AutoMirrored.Filled.DirectionsRun
    override val navigationLabelResId = R.string.main_navigation_item_title_calories
    override val topAppBarLabelResId = R.string.calories_toolbar_title
    override val route = "calories"
}

val topLevelRunningDestinations = listOf(
    RouteBrowse,
    WorkoutList,
    Calories,
)

object RouteDetails : RunningDestination {
    override val topAppBarLabelResId = R.string.route_details_toolbar_title
    override val route = "routes/details/{index}"
    val routeArguments = listOf(navArgument("index") { type = NavType.IntType })
}

object WorkoutCreate : RunningDestination {
    override val topAppBarLabelResId = R.string.workout_create_toolbar_title
    override val route = "workouts/create"
}