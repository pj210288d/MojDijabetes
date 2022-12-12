package rs.etf.running.ui.elements.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import rs.etf.running.RouteDetails
import rs.etf.running.ui.elements.composables.RouteDescriptionHeader
import rs.etf.running.ui.stateholders.RouteViewModel

@Composable
fun RouteDetailsScreen(
    routeIndex: Int,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RouteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val route = uiState.routes[routeIndex]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = RouteDetails.topAppBarLabelResId)) },
                navigationIcon = {
                    IconButton(onClick = { onNavigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
        modifier = modifier,
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .verticalScroll(rememberScrollState()),
        ) {
            RouteDescriptionHeader(route = route)
            Text(
                text = stringResource(id = route.description),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 8.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
            )
        }
    }
}