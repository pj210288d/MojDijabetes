package rs.etf.running.ui.elements.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import rs.etf.running.ui.elements.composables.RouteDescriptionHeader
import rs.etf.running.ui.stateholders.RouteViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RouteDetailsScreen(
    routeIndex: Int,
    isWidthCompact: Boolean,
    modifier: Modifier = Modifier,
    viewModel: RouteViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val route = uiState.routes[routeIndex]

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
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