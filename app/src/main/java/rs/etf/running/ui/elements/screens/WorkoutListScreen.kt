package rs.etf.running.ui.elements.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import rs.etf.running.WorkoutList

@Composable
fun WorkoutListScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = WorkoutList.topAppBarLabelResId)) },
            )
        },
        modifier = modifier,
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {

        }
    }
}