package rs.etf.running.ui.elements.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import rs.etf.running.ui.stateholders.RouteViewModel
import rs.etf.running.R
import rs.etf.running.RouteBrowse
import rs.etf.running.ui.elements.composables.RouteDescriptionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteBrowseScreen(
    onClickRoute: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RouteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = RouteBrowse.topAppBarLabelResId)) },
            )
        },
        modifier = modifier,
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            itemsIndexed(items = uiState.routes) { routeIndex, route ->
                Card {
                    Column {
                        RouteDescriptionHeader(route = route)
                        Row(
                            modifier = Modifier.padding(
                                start = 16.dp,
                                top = 8.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            ),
                        ) {
                            TextButton(
                                onClick = { onClickRoute(routeIndex) },
                            ) {
                                Text(text = stringResource(id = R.string.route_view_holder_button_text_description).uppercase())
                            }
                            val routeLocation = stringResource(id = route.location)
                                .replace(" ", "%20")
                                .replace(",", "%2C")
                            TextButton(
                                onClick = {
                                    val intent = Intent().apply {
                                        action = Intent.ACTION_VIEW
                                        data = Uri.parse("geo:0,0?q=${routeLocation}")
                                    }
                                    ContextCompat.startActivity(context, intent, null)
                                },
                            ) {
                                Text(text = stringResource(id = R.string.route_view_holder_button_text_location).uppercase())
                            }
                        }
                    }
                }
            }
        }
    }
}