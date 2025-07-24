package rs.etf.mojdijabetes.screens.login


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.res.stringResource
import rs.etf.mojdijabetes.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) {
            onLoginSuccess()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (state.isLoginMode) "Prijava" else "Registracija"
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Email polje
            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(LoginEvent.EmailChanged(it)) },
                label = { Text(stringResource(R.string.email)) },
                modifier = Modifier.fillMaxWidth(),
                isError = state.errorMessage?.contains("email") == true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Polje za lozinku
            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onEvent(LoginEvent.PasswordChanged(it)) },
                label = { Text(stringResource(R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = state.errorMessage?.contains("lozinka") == true
            )

            // Potvrda lozinke (samo za registraciju)
            if (!state.isLoginMode) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = state.confirmPassword,
                    onValueChange = { viewModel.onEvent(LoginEvent.ConfirmPasswordChanged(it)) },
                    label = { Text(stringResource(R.string.confirm_password)) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.errorMessage?.contains("poklapaju") == true
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            if (!state.isLoginMode) {
                // Dodatna polja za registraciju
                OutlinedTextField(
                    value = state.firstName,
                    onValueChange = { viewModel.onEvent(LoginEvent.FirstNameChanged(it)) },
                    label = { Text(stringResource(R.string.first_name)) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.lastName,
                    onValueChange = { viewModel.onEvent(LoginEvent.LastNameChanged(it)) },
                    label = { Text(stringResource(R.string.last_name)) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.age,
                    onValueChange = { viewModel.onEvent(LoginEvent.AgeChanged(it)) },
                    label = { Text(stringResource(R.string.age)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                // Sekcija za unos insulina
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "Insulini",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = state.currentInsulin,
                                onValueChange = { viewModel.onEvent(LoginEvent.CurrentInsulinChanged(it)) },
                                label = { Text(stringResource(R.string.insulin_name)) },
                                modifier = Modifier.weight(1f)
                            )

                            IconButton(
                                onClick = { viewModel.onEvent(LoginEvent.AddInsulin) },
                                enabled = state.currentInsulin.isNotBlank()
                            ) {
                                Icon(Icons.Default.Add, "Dodaj insulin")
                            }
                        }

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            state.insulins.forEach { insulin ->
                                AssistChip(
                                    onClick = { },
                                    label = { Text(insulin) },
                                    trailingIcon = {
                                        IconButton(
                                            onClick = { viewModel.onEvent(LoginEvent.RemoveInsulin(insulin)) }
                                        ) {
                                            Icon(
                                                Icons.Default.Close,
                                                contentDescription = "Ukloni insulin",
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
            // Prikaz greške
            state.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Dugme za prijavu/registraciju
            Button(
                onClick = { viewModel.onEvent(LoginEvent.Submit) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(if (state.isLoginMode) "Prijavi se" else "Registruj se")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dugme za promenu moda (prijava/registracija)
            TextButton(
                onClick = { viewModel.onEvent(LoginEvent.ToggleMode) }
            ) {
                Text(
                    if (state.isLoginMode)
                        "Nemaš nalog? Registruj se"
                    else
                        "Već imaš nalog? Prijavi se"
                )
            }
        }
    }
}