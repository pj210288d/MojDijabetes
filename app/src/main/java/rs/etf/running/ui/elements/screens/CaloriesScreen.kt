package rs.etf.running.ui.elements.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import rs.etf.running.R
import rs.etf.running.ui.elements.composables.RadioButtonWithText

@Composable
fun CaloriesScreen(modifier: Modifier = Modifier) {
    val focusManager = LocalFocusManager.current

    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var isMale by remember { mutableStateOf(false) }
    var isFemale by remember { mutableStateOf(false) }


    // https://developer.android.com/jetpack/compose/layouts/basics
    Column(modifier = modifier) {
        Row {
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.calories_edit_text_hint_weight)) },
                value = weight,
                onValueChange = { weight = it },
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
                onValueChange = { height = it },
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

        Row {
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.calories_edit_text_hint_age)) },
                value = age,
                onValueChange = { age = it },
                trailingIcon = { Text(text = stringResource(id = R.string.calories_edit_text_suffix_age)) },
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

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                RadioButtonWithText(
                    text = stringResource(id = R.string.calories_radio_button_text_male),
                    selected = isMale,
                    onClick = {
                        isMale = true
                        isFemale = false
                    },
                )
                RadioButtonWithText(
                    text = stringResource(id = R.string.calories_radio_button_text_female),
                    selected = isFemale,
                    onClick = {
                        isFemale = true
                        isMale = false
                    },
                )
            }
        }
    }
}