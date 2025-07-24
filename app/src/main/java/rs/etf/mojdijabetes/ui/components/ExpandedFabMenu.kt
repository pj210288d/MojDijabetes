package rs.etf.mojdijabetes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ExpandedFabMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onAddGlucose: () -> Unit,
    onAddInsulin: () -> Unit,
    onAddMeal: () -> Unit
) {
    Column (
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        AnimatedVisibility (
            visible = expanded,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Dugme za dodavanje glikemije
                SmallFloatingActionButton(
                    onClick = {
                        onExpandedChange(false)
                        onAddGlucose()
                    }
                ) {
                    Row (
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Glikemija")
                        Icon(Icons.Default.Bloodtype, contentDescription = null)
                    }
                }

                // Dugme za dodavanje insulina
                SmallFloatingActionButton(
                    onClick = {
                        onExpandedChange(false)
                        onAddInsulin()
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Insulin")
                        Icon(Icons.Default.Medication, contentDescription = null)
                    }
                }

                // Dugme za dodavanje obroka
                SmallFloatingActionButton(
                    onClick = {
                        onExpandedChange(false)
                        onAddMeal()
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Obrok")
                        Icon(Icons.Default.Restaurant, contentDescription = null)
                    }
                }
            }
        }

        // Glavni FAB
        FloatingActionButton (
            onClick = { onExpandedChange(!expanded) }
        ) {
            Icon(
                imageVector = if (expanded) Icons.Default.Close else Icons.Default.Add,
                contentDescription = if (expanded) "Zatvori meni" else "Otvori meni"
            )
        }
    }
}