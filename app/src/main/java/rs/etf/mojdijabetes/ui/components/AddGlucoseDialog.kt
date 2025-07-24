package rs.etf.mojdijabetes.ui.components
import android.annotation.SuppressLint
import android.util.Log
import rs.etf.mojdijabetes.R
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TimePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import rs.etf.mojdijabetes.ui.viewmodel.AddGlucoseViewModel
import rs.etf.mojdijabetes.ui.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.math.roundToInt
import kotlin.ranges.coerceIn

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun AddGlucoseDialog(
    onDismiss: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    viewModel: AddGlucoseViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val userInsulins = homeViewModel.userInsulins.collectAsState().value
    val currentUserEmail = homeViewModel.currentUserEmail.collectAsState().value

    var expanded by remember { mutableStateOf(false) }
    var selectedInsulin by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(Date()) }
    var selectedTime by remember { mutableStateOf(Date()) }
    var insulinAmount by remember { mutableIntStateOf(0) }
    var showInsulinDial by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = state.isSaved) {
        if (state.isSaved) {
            onDismiss()
            viewModel.resetIsSaved()
            selectedInsulin = ""
            insulinAmount = 0
            viewModel.onInsulinAmountChange(0)
            viewModel.onValueChange("") // Reset the value in the ViewModel
            viewModel.onMealChange("") // Reset the meal in the ViewModel
            // Keep the current date and time
            selectedTime = Date()
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Dodaj unos glukoze",
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.value,
                    onValueChange = {
                        if (it.isEmpty() || it.toFloatOrNull() != null) {
                            viewModel.onValueChange(it)
                        }
                    },
                    label = { Text(stringResource(R.string.value)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { showDatePicker = true }) {
                        Text(text = SimpleDateFormat("dd.MM.yyyy").format(selectedDate))
                    }
                    Button(onClick = { showTimePicker = true }) {
                        Text(text = SimpleDateFormat("HH:mm").format(selectedTime))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedInsulin,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.select_insulin)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.no_insulin)) },
                            onClick = {
                                selectedInsulin = "Bez insulina"
                                viewModel.onInsulinNameChange("Bez insulina")
                                expanded = false
                                insulinAmount = 0 // Reset insulin amount here
                                viewModel.onInsulinAmountChange(0)
                            }
                        )
                        userInsulins.forEach { insulin ->
                            DropdownMenuItem(
                                text = { Text(insulin) },
                                onClick = {
                                    selectedInsulin = insulin
                                    viewModel.onInsulinNameChange(insulin)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                if (selectedInsulin != "Bez insulina" && selectedInsulin != "") {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Broj Jedinica: ", style = MaterialTheme.typography.bodyLarge)
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            TextButton(onClick = { showInsulinDial = true }) {
                                Text(text = insulinAmount.toString())
                            }
                            if (showInsulinDial) {
                                InsulinAmountDial(
                                    value = insulinAmount,
                                    onValueChange = {
                                        insulinAmount = it
                                        viewModel.onInsulinAmountChange(it)
                                    },
                                    onDismiss = { showInsulinDial = false }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.meal,
                    onValueChange = { viewModel.onMealChange(it) },
                    label = { Text(stringResource(R.string.meal)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val calendar = Calendar.getInstance()
                        calendar.time = selectedDate
                        val timeCalendar = Calendar.getInstance()
                        timeCalendar.time = selectedTime
                        calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY))
                        calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE))
                        viewModel.onDateTimeChange(calendar.timeInMillis)
                        currentUserEmail?.let { viewModel.saveGlucoseEntry(it) }
                        insulinAmount = 0 // Reset insulin amount here
                        viewModel.onInsulinAmountChange(0)
                    },
                    enabled = state.value.isNotEmpty()
                ) {
                    Text(stringResource(R.string.add))
                }
            }
        }
    }

    // Date Picker Dialog
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate.time
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    selectedDate = Date(datePickerState.selectedDateMillis!!)
                }) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    }

    // Time Picker Dialog
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            initialMinute = Calendar.getInstance().get(Calendar.MINUTE),
            is24Hour = true
        )
        TimePickerDialog(
            onDismiss = { showTimePicker = false },
            onConfirm = {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                calendar.set(Calendar.MINUTE, timePickerState.minute)
                selectedTime = calendar.time
                showTimePicker = false
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }
}

@Composable
fun InsulinAmountDial(value: Int, onValueChange: (Int) -> Unit, onDismiss: () -> Unit) {
    var offset by remember { mutableStateOf(0f) }
    var currentValue by remember { mutableIntStateOf(value) }
    var isDragging by remember { mutableStateOf(false) }
    val visibleRange = 1
    var initialClick by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    isDragging = true
                    initialClick = false
                    offset += delta
                    // Reduced sensitivity: Adjust the step size
                    val step = 100f / 5f // Increased step size for less sensitivity
                    val steps = (offset / step).roundToInt()
                    if (steps != 0) {
                        currentValue = (currentValue - steps).coerceIn(1, 149)
                        onValueChange(currentValue)
                        offset -= steps * step
                    }
                },
                onDragStopped = {
                    isDragging = false
                    onDismiss()
                },
                onDragStarted = {
                    isDragging = true
                    initialClick = false
                }
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isDragging) {
                // Popup preview
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for (i in -visibleRange..visibleRange) {
                        val previewValue = (currentValue + i).coerceIn(1, 149)
                        Text(
                            text = previewValue.toString(),
                            style = if (previewValue == currentValue) MaterialTheme.typography.headlineSmall else MaterialTheme.typography.bodyLarge,
                            color = if (previewValue == currentValue) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            } else {
                Text(text = currentValue.toString(), style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}