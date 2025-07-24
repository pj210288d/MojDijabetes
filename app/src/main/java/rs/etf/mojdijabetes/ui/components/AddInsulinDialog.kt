package rs.etf.mojdijabetes.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.res.stringResource
import rs.etf.mojdijabetes.R
import androidx.core.graphics.set
import androidx.hilt.navigation.compose.hiltViewModel
import rs.etf.mojdijabetes.ui.viewmodel.AddInsulinViewModel
import rs.etf.mojdijabetes.ui.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInsulinDialog(
    onDismiss: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    viewModel: AddInsulinViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val userInsulins = homeViewModel.userInsulins.collectAsState().value
    var expanded by remember { mutableStateOf(false) }
    var selectedInsulin by remember { mutableStateOf("") }
    var insulinAmount by remember { mutableIntStateOf(0) }
    var selectedDate by remember { mutableStateOf(Date()) }
    var selectedTime by remember { mutableStateOf(Date()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = state.isSaved) {
        if (state.isSaved) {
            onDismiss()
            viewModel.resetIsSaved()
            selectedInsulin = ""
            insulinAmount = 0
            selectedDate = Date()
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
                        text = "Dodaj insulin",
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedInsulin,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        label = { Text(stringResource(R.string.insulin)) }
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
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
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.no_insulin)) },
                            onClick = {
                                selectedInsulin = "Bez insulina"
                                viewModel.onInsulinNameChange("Bez insulina")
                                expanded = false
                                insulinAmount = 0
                                viewModel.onInsulinAmountChange(0)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                InsulinAmountDial(
                    value = insulinAmount,
                    onValueChange = {
                        insulinAmount = it
                        viewModel.onInsulinAmountChange(it)
                    },
                    onDismiss = {}
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

                Button(
                    onClick = {
                        val calendar = Calendar.getInstance()
                        calendar.time = selectedDate
                        val timeCalendar = Calendar.getInstance()
                        timeCalendar.time = selectedTime
                        calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY))
                        calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE))
                        viewModel.onDateTimeChange(calendar.timeInMillis)
                        homeViewModel.currentUserEmail.value?.let { viewModel.saveInsulinEntry(it) }
                    },
                    enabled = selectedInsulin.isNotEmpty() && insulinAmount >= 0
                ) {
                    Text(stringResource(R.string.add))
                }
            }
        }
    }
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate.time
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        selectedDate = Date(it)
                    }
                    showDatePicker = false
                }) {
                    Text(stringResource(R.string.confirm))
                }
            },
            dismissButton = {
                Button(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.cancel_action))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = SimpleDateFormat("HH").format(selectedTime).toInt(),
            initialMinute = SimpleDateFormat("mm").format(selectedTime).toInt(),
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