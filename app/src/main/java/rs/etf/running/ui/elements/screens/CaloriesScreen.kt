package rs.etf.running.ui.elements.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rs.etf.running.R
import rs.etf.running.ui.elements.composables.RadioButtonWithText
import rs.etf.running.ui.elements.composables.Spinner
import rs.etf.running.ui.stateholders.CaloriesViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CaloriesScreen(
    isWidthCompact: Boolean,
    modifier: Modifier = Modifier,
    caloriesViewModel: CaloriesViewModel = viewModel(),
) {
    val uiState by caloriesViewModel.uiState.collectAsState()

    if (isWidthCompact) {
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            CaloriesInputs(
                weight = uiState.weight,
                onWeightChange = caloriesViewModel::setWeight,
                height = uiState.height,
                onHeightChange = caloriesViewModel::setHeight,
                age = uiState.age,
                onAgeChange = caloriesViewModel::setAge,
                isMale = uiState.isMale,
                onIsMaleClick = caloriesViewModel::setIsMale,
                isFemale = uiState.isFemale,
                onIsFemaleClick = caloriesViewModel::setIsFemale,
                duration = uiState.duration,
                onDurationChange = caloriesViewModel::setDuration,
                metIndex = uiState.metIndex,
                onMetIndexChange = caloriesViewModel::setMetIndex,
            )
            CaloriesCalculation(
                caloriesBurned = uiState.caloriesBurned,
                caloriesNeeded = uiState.caloriesNeeded,
                calculateCalories = caloriesViewModel::calculateCalories,
                modifier = Modifier.fillMaxWidth()
            )
        }
    } else {
        Row {
            Column(modifier = modifier
                .weight(2f)
                .verticalScroll(rememberScrollState())) {
                CaloriesInputs(
                    weight = uiState.weight,
                    onWeightChange = caloriesViewModel::setWeight,
                    height = uiState.height,
                    onHeightChange = caloriesViewModel::setHeight,
                    age = uiState.age,
                    onAgeChange = caloriesViewModel::setAge,
                    isMale = uiState.isMale,
                    onIsMaleClick = caloriesViewModel::setIsMale,
                    isFemale = uiState.isFemale,
                    onIsFemaleClick = caloriesViewModel::setIsFemale,
                    duration = uiState.duration,
                    onDurationChange = caloriesViewModel::setDuration,
                    metIndex = uiState.metIndex,
                    onMetIndexChange = caloriesViewModel::setMetIndex,
                )
            }
            Column(modifier = modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())) {
                CaloriesCalculation(
                    caloriesBurned = uiState.caloriesBurned,
                    caloriesNeeded = uiState.caloriesNeeded,
                    calculateCalories = caloriesViewModel::calculateCalories,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun CaloriesInputs(
    weight: String,
    onWeightChange: (String) -> Unit,
    height: String,
    onHeightChange: (String) -> Unit,
    age: String,
    onAgeChange: (String) -> Unit,
    isMale: Boolean,
    onIsMaleClick: () -> Unit,
    isFemale: Boolean,
    onIsFemaleClick: () -> Unit,
    duration: String,
    onDurationChange: (String) -> Unit,
    metIndex: Int,
    onMetIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    val metOptions = stringArrayResource(id = R.array.met_strings).asList()

    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.calories_edit_text_hint_weight)) },
                value = weight,
                onValueChange = onWeightChange,
                trailingIcon = { Text(text = stringResource(id = R.string.calories_edit_text_suffix_weight)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Right)
                }),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.calories_edit_text_hint_height)) },
                value = height,
                onValueChange = onHeightChange,
                trailingIcon = { Text(text = stringResource(id = R.string.calories_edit_text_suffix_height)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
        }
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.calories_edit_text_hint_age)) },
                value = age,
                onValueChange = onAgeChange,
                trailingIcon = { Text(text = stringResource(id = R.string.calories_edit_text_suffix_age)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                RadioButtonWithText(
                    text = stringResource(id = R.string.calories_radio_button_text_male),
                    selected = isMale,
                    onClick = onIsMaleClick,
                )
                RadioButtonWithText(
                    text = stringResource(id = R.string.calories_radio_button_text_female),
                    selected = isFemale,
                    onClick = onIsFemaleClick,
                )
            }
        }
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.calories_edit_text_hint_duration)) },
                value = duration,
                onValueChange = onDurationChange,
                trailingIcon = { Text(text = stringResource(id = R.string.calories_edit_text_suffix_duration)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically)
            )
            Spinner(
                value = metOptions[metIndex],
                onSelect = { metOption -> onMetIndexChange(metOptions.indexOfFirst { it == metOption }) },
                options = metOptions,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically),
            )
        }
    }
}

@Composable
fun CaloriesCalculation(
    caloriesBurned: Double?,
    caloriesNeeded: Double?,
    calculateCalories: () -> Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        Button(
            onClick = {
                if (!calculateCalories()) {
                    Toast.makeText(context, R.string.calories_toast_text_error, Toast.LENGTH_SHORT)
                        .show()
                }
            },
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(id = R.string.calories_button_text_calculate).uppercase())
        }
        if (caloriesBurned != null) {
            Text(
                text = stringResource(
                    id = R.string.calories_burned,
                    "%.2f".format(caloriesBurned)
                ),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
            )
        }
        if (caloriesNeeded != null) {
            Text(
                text = stringResource(
                    id = R.string.calories_needed,
                    "%.2f".format(caloriesNeeded)
                ),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
            )
        }
    }
}