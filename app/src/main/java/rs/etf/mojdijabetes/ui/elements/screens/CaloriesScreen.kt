//package rs.etf.mojdijabetes.ui.elements.screens
//
//import android.content.ActivityNotFoundException
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusDirection
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalFocusManager
//import androidx.compose.ui.res.stringArrayResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.core.content.ContextCompat
//import androidx.hilt.navigation.compose.hiltViewModel
//import rs.etf.mojdijabetes.Calories
//import rs.etf.mojdijabetes.R
//import rs.etf.mojdijabetes.ui.elements.composables.RadioButtonWithText
//import rs.etf.mojdijabetes.ui.elements.composables.Spinner
//import rs.etf.mojdijabetes.ui.stateholders.CaloriesViewModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CaloriesScreen(
//    isWidthCompact: Boolean,
//    modifier: Modifier = Modifier,
//    caloriesViewModel: CaloriesViewModel = hiltViewModel(),
//) {
//    val uiState by caloriesViewModel.uiState.collectAsState()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(text = stringResource(id = Calories.topAppBarLabelResId)) },
//            )
//        },
//        modifier = modifier,
//    ) { padding ->
//        if (isWidthCompact) {
//            Column(
//                modifier = Modifier
//                    .padding(padding)
//                    .verticalScroll(rememberScrollState()),
//            ) {
//                CaloriesInputs(
//                    weight = uiState.weight,
//                    onWeightChange = caloriesViewModel::setWeight,
//                    height = uiState.height,
//                    onHeightChange = caloriesViewModel::setHeight,
//                    age = uiState.age,
//                    onAgeChange = caloriesViewModel::setAge,
//                    isMale = uiState.isMale,
//                    onIsMaleClick = caloriesViewModel::setIsMale,
//                    isFemale = uiState.isFemale,
//                    onIsFemaleClick = caloriesViewModel::setIsFemale,
//                    duration = uiState.duration,
//                    onDurationChange = caloriesViewModel::setDuration,
//                    metIndex = uiState.metIndex,
//                    onMetIndexChange = caloriesViewModel::setMetIndex,
//                )
//                CaloriesCalculation(
//                    caloriesBurned = uiState.caloriesBurned,
//                    caloriesNeeded = uiState.caloriesNeeded,
//                    calculateCalories = caloriesViewModel::calculateCalories,
//                    modifier = Modifier.fillMaxWidth()
//                )
//            }
//        } else {
//            Row {
//                Column(
//                    modifier = modifier
//                        .weight(2f)
//                        .verticalScroll(rememberScrollState())
//                ) {
//                    CaloriesInputs(
//                        weight = uiState.weight,
//                        onWeightChange = caloriesViewModel::setWeight,
//                        height = uiState.height,
//                        onHeightChange = caloriesViewModel::setHeight,
//                        age = uiState.age,
//                        onAgeChange = caloriesViewModel::setAge,
//                        isMale = uiState.isMale,
//                        onIsMaleClick = caloriesViewModel::setIsMale,
//                        isFemale = uiState.isFemale,
//                        onIsFemaleClick = caloriesViewModel::setIsFemale,
//                        duration = uiState.duration,
//                        onDurationChange = caloriesViewModel::setDuration,
//                        metIndex = uiState.metIndex,
//                        onMetIndexChange = caloriesViewModel::setMetIndex,
//                    )
//                }
//                Column(
//                    modifier = modifier
//                        .weight(1f)
//                        .verticalScroll(rememberScrollState())
//                ) {
//                    CaloriesCalculation(
//                        caloriesBurned = uiState.caloriesBurned,
//                        caloriesNeeded = uiState.caloriesNeeded,
//                        calculateCalories = caloriesViewModel::calculateCalories,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun CaloriesInputs(
//    weight: String,
//    onWeightChange: (String) -> Unit,
//    height: String,
//    onHeightChange: (String) -> Unit,
//    age: String,
//    onAgeChange: (String) -> Unit,
//    isMale: Boolean,
//    onIsMaleClick: () -> Unit,
//    isFemale: Boolean,
//    onIsFemaleClick: () -> Unit,
//    duration: String,
//    onDurationChange: (String) -> Unit,
//    metIndex: Int,
//    onMetIndexChange: (Int) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val focusManager = LocalFocusManager.current
//
//    val metOptions = stringArrayResource(id = R.array.met_strings).asList()
//
//    Column(modifier = modifier) {
//        Row(modifier = Modifier.padding(vertical = 8.dp)) {
//            OutlinedTextField(
//                label = { Text(text = stringResource(id = R.string.calories_edit_text_hint_weight)) },
//                value = weight,
//                onValueChange = onWeightChange,
//                trailingIcon = { Text(text = stringResource(id = R.string.calories_edit_text_suffix_weight)) },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    keyboardType = KeyboardType.Number,
//                    imeAction = ImeAction.Next,
//                ),
//                keyboardActions = KeyboardActions(onNext = {
//                    focusManager.moveFocus(FocusDirection.Right)
//                }),
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(horizontal = 16.dp)
//            )
//            OutlinedTextField(
//                label = { Text(text = stringResource(id = R.string.calories_edit_text_hint_height)) },
//                value = height,
//                onValueChange = onHeightChange,
//                trailingIcon = { Text(text = stringResource(id = R.string.calories_edit_text_suffix_height)) },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    keyboardType = KeyboardType.Number,
//                    imeAction = ImeAction.Next,
//                ),
//                keyboardActions = KeyboardActions(onNext = {
//                    focusManager.moveFocus(FocusDirection.Down)
//                }),
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(horizontal = 16.dp)
//            )
//        }
//        Row(modifier = Modifier.padding(vertical = 8.dp)) {
//            OutlinedTextField(
//                label = { Text(text = stringResource(id = R.string.calories_edit_text_hint_age)) },
//                value = age,
//                onValueChange = onAgeChange,
//                trailingIcon = { Text(text = stringResource(id = R.string.calories_edit_text_suffix_age)) },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    keyboardType = KeyboardType.Number,
//                    imeAction = ImeAction.Next,
//                ),
//                keyboardActions = KeyboardActions(onNext = {
//                    focusManager.moveFocus(FocusDirection.Down)
//                }),
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(horizontal = 16.dp)
//                    .align(Alignment.CenterVertically)
//            )
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(horizontal = 16.dp)
//            ) {
//                RadioButtonWithText(
//                    text = stringResource(id = R.string.calories_radio_button_text_male),
//                    selected = isMale,
//                    onClick = onIsMaleClick,
//                )
//                RadioButtonWithText(
//                    text = stringResource(id = R.string.calories_radio_button_text_female),
//                    selected = isFemale,
//                    onClick = onIsFemaleClick,
//                )
//            }
//        }
//        Row(modifier = Modifier.padding(vertical = 8.dp)) {
//            OutlinedTextField(
//                label = { Text(text = stringResource(id = R.string.calories_edit_text_hint_duration)) },
//                value = duration,
//                onValueChange = onDurationChange,
//                trailingIcon = { Text(text = stringResource(id = R.string.calories_edit_text_suffix_duration)) },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    keyboardType = KeyboardType.Number,
//                    imeAction = ImeAction.Done,
//                ),
//                keyboardActions = KeyboardActions(onDone = {
//                    focusManager.clearFocus()
//                }),
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(horizontal = 16.dp)
//                    .align(Alignment.CenterVertically)
//            )
//            Spinner(
//                value = metOptions[metIndex],
//                onSelect = { metOption -> onMetIndexChange(metOptions.indexOfFirst { it == metOption }) },
//                options = metOptions,
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(horizontal = 16.dp)
//                    .align(Alignment.CenterVertically),
//            )
//        }
//    }
//}
//
//@Composable
//fun CaloriesCalculation(
//    caloriesBurned: Double?,
//    caloriesNeeded: Double?,
//    calculateCalories: () -> Boolean,
//    modifier: Modifier = Modifier
//) {
//    val context = LocalContext.current
//
//    val mailSubject = stringResource(id = R.string.calories_intent_mail_subject)
//    val mailText = stringResource(id = R.string.calories_intent_mail_text, caloriesBurned ?: 0)
//    val chooserTitle = stringResource(id = R.string.calories_intent_chooser_title)
//
//
//    Column(modifier = modifier) {
//        Button(
//            onClick = {
//                if (!calculateCalories()) {
//                    Toast.makeText(context, R.string.calories_toast_text_error, Toast.LENGTH_SHORT)
//                        .show()
//                }
//            },
//            modifier = Modifier
//                .padding(vertical = 16.dp)
//                .align(Alignment.CenterHorizontally)
//        ) {
//            Text(text = stringResource(id = R.string.calories_button_text_calculate).uppercase())
//        }
//        if (caloriesBurned != null) {
//            Button(
//                onClick = {
//                    // https://developer.android.com/training/basics/intents/sending
//                    val intent = Intent(Intent.ACTION_SEND).apply {
//                        type = "text/plain"
//                        putExtra(Intent.EXTRA_SUBJECT, mailSubject)
//                        putExtra(Intent.EXTRA_TEXT, mailText)
//                    }
//                    val chooser = Intent.createChooser(intent, chooserTitle)
//                    try {
//                        ContextCompat.startActivity(context, chooser, Bundle())
//                    } catch (e: ActivityNotFoundException) {
//                        // nothing
//                    }
//                },
//                modifier = Modifier
//                    .padding(vertical = 16.dp)
//                    .align(Alignment.CenterHorizontally)
//            ) {
//                Text(text = stringResource(id = R.string.calories_button_text_share).uppercase())
//            }
//        }
//        if (caloriesBurned != null) {
//            Text(
//                text = stringResource(
//                    id = R.string.calories_burned,
//                    "%.2f".format(caloriesBurned)
//                ),
//                fontSize = 20.sp,
//                modifier = Modifier
//                    .padding(vertical = 16.dp)
//                    .align(Alignment.CenterHorizontally),
//            )
//        }
//        if (caloriesNeeded != null) {
//            Text(
//                text = stringResource(
//                    id = R.string.calories_needed,
//                    "%.2f".format(caloriesNeeded)
//                ),
//                fontSize = 20.sp,
//                modifier = Modifier
//                    .padding(vertical = 16.dp)
//                    .align(Alignment.CenterHorizontally),
//            )
//        }
//    }
//}