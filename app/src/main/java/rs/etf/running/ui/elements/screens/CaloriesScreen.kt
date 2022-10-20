package rs.etf.running.ui.elements.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import rs.etf.running.R

@Composable
fun CaloriesScreen(modifier: Modifier = Modifier) {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }

    OutlinedTextField(
        label = { Text(text = stringResource(id = R.string.calories_edit_text_hint_weight)) },
        value = weight,
        onValueChange = { weight = it },
        trailingIcon = { Text(text = stringResource(id = R.string.calories_edit_text_suffix_weight)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
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
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
    )
}