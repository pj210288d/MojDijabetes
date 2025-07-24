package rs.etf.mojdijabetes.screens.home




import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.layout.fullWidth
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.ChartScrollState
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.chart.line.LineChart.LineSpec
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.scroll.InitialScroll
import dagger.hilt.EntryPoint
import rs.etf.mojdijabetes.ui.components.AddGlucoseDialog
import rs.etf.mojdijabetes.ui.components.AddInsulinDialog
import rs.etf.mojdijabetes.ui.components.ExpandedFabMenu
import rs.etf.mojdijabetes.ui.viewmodel.AddInsulinViewModel
import rs.etf.mojdijabetes.ui.viewmodel.GlucoseEntry
import rs.etf.mojdijabetes.ui.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs
import kotlin.time.Duration.Companion.hours
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.offset

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    insulinViewModel: AddInsulinViewModel = hiltViewModel()
) {
    var fabExpanded by remember { mutableStateOf(false) }
    var showGlucoseDialog by remember { mutableStateOf(false) }
    var showInsulinDialog by remember { mutableStateOf(false) }
    var showMealDialog by remember { mutableStateOf(false) }
    val userInsulins by viewModel.userInsulins.collectAsState()
    val selectedInsulin by viewModel.selectedInsulin.collectAsState()
    val currentUserEmailState by viewModel.currentUserEmail.collectAsState()
    val currentUserEmail = currentUserEmailState ?: ""
    val glucoseEntries by viewModel.glucoseEntries.collectAsState()
    val datasetLineSpec = remember { arrayListOf<LineChart.LineSpec>() }
    val scrollState = rememberChartScrollState()
    val modelProducer = remember { ChartEntryModelProducer() }
    val dataPoints = remember { mutableStateListOf<FloatEntry>() }
    var resetScroll by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    var selectedDate by remember { mutableStateOf(datePickerState.selectedDateMillis ?: System.currentTimeMillis()) }
    var showDatePickerDialog by remember { mutableStateOf(false) }

    // Swipe gesture state
    val swipeThreshold = 100f
    var swipeStartX by remember { mutableStateOf(0f) }
    var isSwipeInProgress by remember { mutableStateOf(false) }

    // Animation state
    var swipeOffset by remember { mutableStateOf(0f) }
    val animatedSwipeOffset by animateFloatAsState(
        targetValue = swipeOffset,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "swipe_animation"
    ) { swipeOffset = 0f }

    // Function to change date
    fun changeDate(daysOffset: Int) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = selectedDate
        calendar.add(Calendar.DAY_OF_MONTH, daysOffset)
        selectedDate = calendar.timeInMillis

        // Update the date picker state as well
        datePickerState.selectedDateMillis = selectedDate

        // Trigger swipe animation
        swipeOffset = if (daysOffset > 0) -10f else 10f
    }

    // Better calendar handling
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = selectedDate
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val startOfDay = calendar.timeInMillis

    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    val endOfDay = calendar.timeInMillis

    val filteredGlucoseEntries = glucoseEntries.filter { it.dateTime in startOfDay..endOfDay }

    // Fixed glucose range
    val fixedMinGlucose = 1.0f
    val fixedMaxGlucose = 35.0f

    // Load data when user email changes
    LaunchedEffect(key1 = currentUserEmail) {
        if (currentUserEmail.isNotEmpty()) {
            viewModel.loadGlucoseEntries(currentUserEmail)
        }
    }

    // Marker style for data points
    val pointMarker = shapeComponent(
        color = Blue,
        shape = com.patrykandpatrick.vico.core.component.shape.Shapes.pillShape,
        strokeWidth = 2.dp,
        strokeColor = Color.Black
    )

    // Helper function to create hour entries for the whole day
    fun createHourEntries(): List<FloatEntry> {
        val hourlyEntries = mutableListOf<FloatEntry>()
        // Create entries for every 30 minutes instead of every hour
        for (hour in 0..23) {
            val cal = Calendar.getInstance()
            cal.timeInMillis = selectedDate
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            hourlyEntries.add(FloatEntry(x = cal.timeInMillis.toFloat(), y = Float.NaN))

        }
        return hourlyEntries
    }

    // Update chart data when glucose entries change
    LaunchedEffect(key1 = filteredGlucoseEntries, key2 = selectedDate) {
        dataPoints.clear()
        val sortedEntries = filteredGlucoseEntries.sortedBy { it.dateTime }

        // Add invisible markers for consistent spacing
        val hourMarkers = createHourEntries()
        //dataPoints.addAll(hourMarkers)
        // Add real glucose entries
        for (glucoseEntry in sortedEntries) {
            Log.d("Filtrirani", "x = ${glucoseEntry.dateTime}, y = ${glucoseEntry.value}")
            val calendar = Calendar.getInstance().apply {
                timeInMillis = glucoseEntry.dateTime
            }
            dataPoints.add(FloatEntry(x = calendar.get(Calendar.HOUR_OF_DAY).toFloat(), y = glucoseEntry.value.toFloat()))
        }

        // Add invisible hour markers if no data or sparse data
        if (filteredGlucoseEntries.isEmpty() || filteredGlucoseEntries.size < 6) {
            // Add invisible hour markers to ensure consistent axis display
            createHourEntries().forEach {
                dataPoints.add(it)
            }
        }

        // Configure line appearance
        val dataLabel = textComponent {
            color = Color.Black.toArgb() // Boja teksta
            textSizeSp = 12f // Veličina teksta
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            padding = dimensionsOf(horizontal = 4.dp, vertical = 2.dp)
        }

        // Dodajemo stil linije sa oznakama vrednosti
        datasetLineSpec.clear()
        datasetLineSpec.add(
            LineSpec(
                lineColor = Blue.toArgb(),
                lineThicknessDp = 3f,
                point = pointMarker,
                pointSizeDp = 8f,
                pointConnector = DefaultPointConnector(cubicStrength = 0.5f),
                //dataLabel = dataLabel // Dodajemo oznake vrednosti
            )
        )
        // Update chart model
        modelProducer.setEntries(listOf(dataPoints))
        resetScroll = true
    }

    // Reset scroll position when data changes
    LaunchedEffect(key1 = resetScroll) {
        if (resetScroll) {
            scrollState.scrollBy(0f)
            resetScroll = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Moj Dijabetes") },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profil"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExpandedFabMenu(
                expanded = fabExpanded,
                onExpandedChange = { fabExpanded = it },
                onAddGlucose = { showGlucoseDialog = true },
                onAddInsulin = { showInsulinDialog = true },
                onAddMeal = { showMealDialog = true }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            swipeStartX = offset.x
                            isSwipeInProgress = true
                        },
                        onDragEnd = {
                            isSwipeInProgress = false
                        },
                        onDrag = { change, _ ->
                            if (isSwipeInProgress) {
                                val deltaX = change.position.x - swipeStartX

                                // Visual feedback during drag
                                swipeOffset = (deltaX * 0.2f).coerceIn(-30f, 30f)

                                // Check if swipe distance is sufficient
                                if (abs(deltaX) > swipeThreshold) {
                                    when {
                                        deltaX > 0 -> {
                                            // Swipe right - go to previous day
                                            changeDate(-1)
                                            isSwipeInProgress = false
                                        }
                                        deltaX < 0 -> {
                                            // Swipe left - go to next day
                                            changeDate(1)
                                            isSwipeInProgress = false
                                        }
                                    }
                                }
                            }
                        }
                    )
                },
        ) {
            Text(
                text = "Moj Dijabetes - Početna",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Date selector with navigation arrows and swipe hint
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Previous day button
                IconButton(
                    onClick = { changeDate(-1) },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Previous day",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // Date display and picker
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Button(
                        onClick = { showDatePickerDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                        Text(text = dateFormat.format(Date(selectedDate)))
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Odaberi datum",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    // Swipe hint text
                    Text(
                        text = "← Swipe to change date →",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // Next day button
                IconButton(
                    onClick = { changeDate(1) },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next day",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Date picker dialog
            if (showDatePickerDialog) {
                DatePickerDialog(
                    onDismissRequest = { showDatePickerDialog = false },
                    confirmButton = {
                        Button(onClick = {
                            showDatePickerDialog = false
                            selectedDate = datePickerState.selectedDateMillis ?: selectedDate
                        }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDatePickerDialog = false }) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            // Display statistics if available
            if (filteredGlucoseEntries.isNotEmpty()) {
                val avgGlucose = filteredGlucoseEntries.map { it.value.toFloat() }.average()
                val minGlucose = filteredGlucoseEntries.minOf { it.value.toFloat() }
                val maxGlucose = filteredGlucoseEntries.maxOf { it.value.toFloat() }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Average", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            String.format("%.1f", avgGlucose),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Min", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            minGlucose.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Max", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            maxGlucose.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Readings", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            filteredGlucoseEntries.size.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            // Glucose entries list
            if (filteredGlucoseEntries.isNotEmpty()) {
                Text(
                    text = "Glucose Readings",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .offset(x = animatedSwipeOffset.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredGlucoseEntries.sortedByDescending { it.dateTime }) { entry ->
                        GlucoseEntryItem(entry = entry)
                    }
                }
            } else {
                // Show message when no entries for selected date
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp)
                        .offset(x = animatedSwipeOffset.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "No data",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No glucose readings for selected date",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tap the + button to add a reading",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    // Dialogs
    if (showGlucoseDialog) {
        AddGlucoseDialog(
            onDismiss = { showGlucoseDialog = false },
            homeViewModel = viewModel
        )
    }

    if (showInsulinDialog) {
        AddInsulinDialog(
            onDismiss = { showInsulinDialog = false },
            homeViewModel = viewModel
        )
    }

    if (showMealDialog) {
        // Need to implement your meal dialog here
        showMealDialog = false
    }
}
@Composable
fun GlucoseEntryItem(entry: GlucoseEntry) {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val glucoseValue = entry.value.toFloat()

    // Define glucose level colors
    val (backgroundColor, textColor) = when {
        glucoseValue < 4.0f -> Pair(Color(0xFFFFEBEE), Color(0xFFD32F2F)) // Low - Red
        glucoseValue > 10.0f -> Pair(Color(0xFFFFF3E0), Color(0xFFF57C00)) // High - Orange
        else -> Pair(Color(0xFFE8F5E8), Color(0xFF2E7D32)) // Normal - Green
    }

    // Check if insulin information is present
    val hasInsulin = !entry.insulinName.isNullOrEmpty() &&
            entry.insulinName != "Bez insulina" &&
            entry.insulinAmount != null &&
            entry.insulinAmount!! > 0

    val hasMeal = !entry.meal.isNullOrEmpty()

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Main row with time and glucose value
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = timeFormat.format(Date(entry.dateTime)),
                        style = MaterialTheme.typography.titleMedium,
                        color = textColor
                    )
                    Text(
                        text = when {
                            glucoseValue < 4.0f -> "Low"
                            glucoseValue > 10.0f -> "High"
                            else -> "Normal"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = textColor.copy(alpha = 0.7f)
                    )
                }

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = String.format("%.1f", glucoseValue),
                        style = MaterialTheme.typography.headlineMedium,
                        color = textColor
                    )
                    Text(
                        text = " mmol/L",
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor.copy(alpha = 0.7f),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }

            // Additional information row
            if (hasInsulin || hasMeal) {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Insulin information
                    if (hasInsulin) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(
                                    color = textColor.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalHospital,
                                contentDescription = "Insulin",
                                tint = textColor.copy(alpha = 0.8f),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${entry.insulinName}: ${entry.insulinAmount}u",
                                style = MaterialTheme.typography.bodySmall,
                                color = textColor.copy(alpha = 0.8f)
                            )
                        }
                    }

                    // Meal information
                    if (hasMeal) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(
                                    color = textColor.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Restaurant,
                                contentDescription = "Meal",
                                tint = textColor.copy(alpha = 0.8f),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = entry.meal!!,
                                style = MaterialTheme.typography.bodySmall,
                                color = textColor.copy(alpha = 0.8f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}