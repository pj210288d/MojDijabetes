package rs.etf.mojdijabetes.ui.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import co.yml.charts.axis.AxisData
import rs.etf.mojdijabetes.ui.components.AddGlucoseDialog
import rs.etf.mojdijabetes.ui.components.AddInsulinDialog
import rs.etf.mojdijabetes.ui.components.ExpandedFabMenu
import rs.etf.mojdijabetes.ui.viewmodel.AddInsulinViewModel
import rs.etf.mojdijabetes.ui.viewmodel.GlucoseEntry
import rs.etf.mojdijabetes.ui.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.text.format
import kotlin.text.maxOfOrNull
import kotlin.text.minOfOrNull
import kotlin.text.toFloat

@Composable
fun GlucoseChart(points: List<GlucoseEntry>){
    val steps = 25
    val xAxisData = AxisData.Builder()
        .axisStepSize(20.dp)
        .backgroundColor(Color.Transparent)
        .steps(points.size - 1)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(15.dp)
        .axisLineColor(Color.Green)
        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
        .build()
    val yAxisData = AxisData.Builder()
        .labelData { i ->
            val yScale = 25 / steps
            (i * yScale).toString() }
        .labelAndAxisLinePadding(5.dp)
        .axisLineColor(Color.Green)
        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
        .build()
}