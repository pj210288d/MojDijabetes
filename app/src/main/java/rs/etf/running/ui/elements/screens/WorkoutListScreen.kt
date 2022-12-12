package rs.etf.running.ui.elements.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import rs.etf.running.WorkoutList
import rs.etf.running.ui.stateholders.WorkoutViewModel
import rs.etf.running.util.DateTimeUtil

@Composable
fun WorkoutListScreen(
    modifier: Modifier = Modifier,
    onNavigateToWorkoutCreate: () -> Unit,
    viewModel: WorkoutViewModel = hiltViewModel(),
) {
    val workouts by viewModel.allWorkouts.collectAsState(initial = listOf())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = WorkoutList.topAppBarLabelResId)) },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToWorkoutCreate() }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        modifier = modifier,
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(workouts) {
                Card(modifier = Modifier.fillMaxWidth(), elevation = 8.dp) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = DateTimeUtil.formatDate(it.date),
                            style = MaterialTheme.typography.body1,
                        )
                        Text(
                            text = it.label,
                            style = MaterialTheme.typography.h6,
                        )
                        Row() {
                            Text(
                                text = String.format("%.2f km", it.distance),
                                style = MaterialTheme.typography.body1,
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                            Text(
                                text = String.format(
                                    "%s min/km",
                                    DateTimeUtil.realMinutesToString(it.duration / it.distance)
                                ),
                                style = MaterialTheme.typography.body1,
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                            Text(
                                text = String.format(
                                    "%s min",
                                    DateTimeUtil.realMinutesToString(it.duration)
                                ),
                                style = MaterialTheme.typography.body1,
                            )
                        }
                    }
                }
            }
        }
    }
}