package rs.etf.running.ui.elements.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import rs.etf.running.ui.stateholders.RouteViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import rs.etf.running.R
import rs.etf.running.ui.elements.composables.RouteDescriptionHeader

@Composable
fun RouteBrowseScreen(
    isWidthCompact: Boolean,
    modifier: Modifier = Modifier,
    viewModel: RouteViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn {
        items(items = uiState.routes) { route ->
            Card {
                Column {
                    RouteDescriptionHeader(route = route)
                    Row(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 8.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                    ) {
                        TextButton(
                            onClick = { /*TODO*/ },
                        ) {
                            Text(text = stringResource(id = R.string.route_view_holder_button_text_description).uppercase())
                        }
                        TextButton(
                            onClick = { /*TODO*/ },
                        ) {
                            Text(text = stringResource(id = R.string.route_view_holder_button_text_location).uppercase())
                        }
                    }
                }
            }
        }
    }
}