package rs.etf.running.ui.elements.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import rs.etf.running.data.Route
import rs.etf.running.R

@Composable
fun RouteDescriptionHeader(route: Route, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Image(
            painter = painterResource(id = route.image),
            contentDescription = stringResource(id = route.name),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
        )
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = stringResource(id = R.string.route_label, route.index),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = stringResource(id = route.name),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 8.dp)
            )
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Text(
                    text = stringResource(
                        id = R.string.route_length_label,
                        route.length,
                    ),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = stringResource(
                        id = R.string.route_difficulty_label,
                        stringResource(id = route.difficulty),
                    ),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(2f),
                )
            }
        }
    }
}