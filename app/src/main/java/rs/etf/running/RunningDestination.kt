package rs.etf.running

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.ui.graphics.vector.ImageVector

interface RunningDestination {
    val topAppBarLabelResId: Int
}

interface TopLevelRunningDestination : RunningDestination {
    val navigationIcon: ImageVector
    val navigationLabelResId: Int
}

object RouteBrowse : TopLevelRunningDestination {
    override val navigationIcon = Icons.Default.Terrain
    override val navigationLabelResId = R.string.main_navigation_item_title_routes
    override val topAppBarLabelResId = R.string.route_browse_toolbar_title
}

object WorkoutList : TopLevelRunningDestination {
    override val navigationIcon = Icons.Default.BarChart
    override val navigationLabelResId = R.string.main_navigation_item_title_workouts
    override val topAppBarLabelResId = R.string.workout_list_toolbar_title
}

object Calories : TopLevelRunningDestination {
    override val navigationIcon = Icons.Default.DirectionsRun
    override val navigationLabelResId = R.string.main_navigation_item_title_calories
    override val topAppBarLabelResId = R.string.calories_toolbar_title
}

val topLevelRunningDestinations = listOf(
    RouteBrowse,
    WorkoutList,
    Calories,
)

object RouteDetails : RunningDestination {
    override val topAppBarLabelResId = R.string.route_details_toolbar_title
}

val runningDestinations = topLevelRunningDestinations + listOf(
    RouteDetails,
)