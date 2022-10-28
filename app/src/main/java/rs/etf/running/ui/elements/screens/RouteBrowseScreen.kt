package rs.etf.running.ui.elements.screens

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import rs.etf.running.ui.stateholders.RouteViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import rs.etf.running.R
import rs.etf.running.ROUTE_INDEX_KEY
import rs.etf.running.TempRouteDetailsActivity
import rs.etf.running.ui.elements.composables.RouteDescriptionHeader

@Composable
fun RouteBrowseScreen(
    isWidthCompact: Boolean,
    modifier: Modifier = Modifier,
    viewModel: RouteViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    LazyColumn {
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
                            onClick = {
                                val intent = Intent().apply {
                                    putExtra(ROUTE_INDEX_KEY, routeIndex)
                                    component = ComponentName(
                                        context,
                                        TempRouteDetailsActivity::class.java
                                    )
                                }
                                ContextCompat.startActivity(context, intent, null)
                            },
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