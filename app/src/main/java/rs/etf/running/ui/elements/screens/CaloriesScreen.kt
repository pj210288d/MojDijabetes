package rs.etf.running.ui.elements.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
    caloriesViewModel: CaloriesViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    // https://developer.android.com/jetpack/compose/libraries#streams
    // https://developer.android.com/reference/kotlin/androidx/compose/runtime/package-summary
    val uiState by caloriesViewModel.uiState.collectAsState()

    val metOptions = stringArrayResource(id = R.array.met_strings).asList()

    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.calories_edit_text_hint_weight)) },
                value = uiState.weight,
                onValueChange = caloriesViewModel::setWeight,
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
                value = uiState.height,
                onValueChange = caloriesViewModel::setHeight,
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
                value = uiState.age,
                onValueChange = caloriesViewModel::setAge,
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
                    selected = uiState.isMale,
                    onClick = caloriesViewModel::setIsMale,
                )
                RadioButtonWithText(
                    text = stringResource(id = R.string.calories_radio_button_text_female),
                    selected = uiState.isFemale,
                    onClick = caloriesViewModel::setIsFemale,
                )
            }
        }
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.calories_edit_text_hint_duration)) },
                value = uiState.duration,
                onValueChange = caloriesViewModel::setDuration,
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
                value = metOptions[uiState.metIndex],
                onSelect = { metOption -> caloriesViewModel.setMetIndex(metOptions.indexOfFirst { it == metOption }) },
                options = metOptions,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically),
            )
        }
        Button(
            onClick = {
                if (!caloriesViewModel.calculateCalories()) {
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
        if (uiState.caloriesBurned != null) {
            Text(
                text = stringResource(
                    id = R.string.calories_burned,
                    "%.2f".format(uiState.caloriesBurned)
                ),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
            )
        }
        if (uiState.caloriesNeeded != null) {
            Text(
                text = stringResource(
                    id = R.string.calories_needed,
                    "%.2f".format(uiState.caloriesNeeded)
                ),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
            )
        }
    }
}