package rs.etf.running.ui.elements.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import rs.etf.running.WorkoutCreate
import java.util.*
import rs.etf.running.R
import rs.etf.running.ui.stateholders.WorkoutViewModel
import rs.etf.running.util.DateTimeUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutCreateScreen(
    onNavigateUp: () -> Unit,
    onWorkoutCreated: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiStateCreate.collectAsState()

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, y: Int, m: Int, d: Int ->
            viewModel.setDate(y, m, d)
        },
        year, month, day,
    )

    val dateInteractionSource = remember { MutableInteractionSource() }
    if (dateInteractionSource.collectIsPressedAsState().value) {
        datePickerDialog.show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = WorkoutCreate.topAppBarLabelResId)) },
                navigationIcon = {
                    IconButton(onClick = { onNavigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
            )
        },
        modifier = modifier,
    ) { padding ->
        Column(modifier = Modifier.padding((padding))) {
            Spacer(modifier = Modifier.size(32.dp))
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.workout_create_edit_text_hint_date)) },
                value = uiState.date.let { if (it != null) DateTimeUtil.formatDate(it) else "" },
                onValueChange = { },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.EditCalendar, contentDescription = null
                    )
                },
                readOnly = true,
                interactionSource = dateInteractionSource,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 64.dp, vertical = 8.dp),
            )
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.workout_create_edit_text_hint_label)) },
                value = uiState.label,
                onValueChange = { viewModel.setLabel(it) },
                singleLine = true,
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 64.dp, vertical = 8.dp),
            )
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.workout_create_edit_text_hint_distance)) },
                value = uiState.distance?.toString() ?: "",
                onValueChange = { viewModel.setDistance(it) },
                trailingIcon = { Text(text = stringResource(id = R.string.workout_create_edit_text_suffix_distance)) },
                singleLine = true,
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 64.dp, vertical = 8.dp),
            )
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.workout_create_edit_text_hint_duration)) },
                value = uiState.duration?.toString() ?: "",
                onValueChange = { viewModel.setDuration(it) },
                trailingIcon = { Text(text = stringResource(id = R.string.workout_create_edit_text_suffix_duration)) },
                singleLine = true,
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 64.dp, vertical = 8.dp),
            )
            Button(
                onClick = {
                    viewModel.insertWorkout()
                    onWorkoutCreated()
                },
                enabled = uiState.date != null &&
                        uiState.label != "" &&
                        uiState.distance != null &&
                        uiState.duration != null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 32.dp),
            ) {
                Text(text = stringResource(id = R.string.workout_create_button_text_create).uppercase())
            }
        }
    }
}