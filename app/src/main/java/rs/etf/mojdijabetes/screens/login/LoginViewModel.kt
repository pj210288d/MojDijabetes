package rs.etf.mojdijabetes.screens.login


import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import rs.etf.mojdijabetes.data.local.UserPreferences
import rs.etf.mojdijabetes.data.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    init {
        // Proveravamo da li je korisnik već ulogovan
        viewModelScope.launch {
            authRepository.isUserLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    _state.value = _state.value.copy(isAuthenticated = true)
                }
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }

    private fun validateRegistrationFields(): Boolean {
        val currentState = _state.value
        return when {
            currentState.firstName.isBlank() -> {
                _state.value = currentState.copy(errorMessage = "Ime je obavezno")
                false
            }

            currentState.lastName.isBlank() -> {
                _state.value = currentState.copy(errorMessage = "Prezime je obavezno")
                false
            }

            currentState.age.isBlank() -> {
                _state.value = currentState.copy(errorMessage = "Godine su obavezne")
                false
            }

            currentState.age.toIntOrNull() == null || currentState.age.toInt() <= 0 -> {
                _state.value = currentState.copy(errorMessage = "Unesite validne godine")
                false
            }

            currentState.insulins.isEmpty() -> {
                _state.value = currentState.copy(errorMessage = "Dodajte bar jedan insulin")
                false
            }

            else -> true
        }
    }

    private fun submitForm() {
        val currentState = _state.value

        // Prvo validiramo unos
        when {
            !isEmailValid(currentState.email) -> {
                _state.value = currentState.copy(
                    errorMessage = "Unesite validnu email adresu"
                )
                return
            }

            !isPasswordValid(currentState.password) -> {
                _state.value = currentState.copy(
                    errorMessage = "Lozinka mora imati najmanje 6 karaktera"
                )
                return
            }

            !currentState.isLoginMode && currentState.password != currentState.confirmPassword -> {
                _state.value = currentState.copy(
                    errorMessage = "Lozinke se ne poklapaju"
                )
                return
            }

            !currentState.isLoginMode && !validateRegistrationFields() -> {
                return
            }
        }

        viewModelScope.launch {
            try {
                _state.value = currentState.copy(isLoading = true)

                if (currentState.isLoginMode) {
                    // Pokušaj prijave
                    authRepository.login(currentState.email, currentState.password).fold(
                        onSuccess = {
                            _state.value = currentState.copy(
                                isLoading = false,
                                isAuthenticated = true
                            )
                        },
                        onFailure = { exception ->
                            _state.value = currentState.copy(
                                isLoading = false,
                                errorMessage = exception.message
                            )
                        }
                    )
                } else {
                    // Pokušaj registracije
                    authRepository.register(
                        email = currentState.email,
                        password = currentState.password,
                        firstName = currentState.firstName,
                        lastName = currentState.lastName,
                        age = currentState.age.toInt(),
                        insulins = currentState.insulins
                    )
                    _state.value = currentState.copy(
                        isLoading = false,
                        isAuthenticated = true
                    )
                }
            } catch (e: Exception) {
                _state.value = currentState.copy(
                    errorMessage = e.message ?: "Došlo je do greške",
                    isLoading = false
                )
            }
        }
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                _state.value = _state.value.copy(
                    email = event.email,
                    errorMessage = null
                )
            }

            is LoginEvent.PasswordChanged -> {
                _state.value = _state.value.copy(
                    password = event.password,
                    errorMessage = null
                )
            }

            is LoginEvent.ConfirmPasswordChanged -> {
                _state.value = _state.value.copy(
                    confirmPassword = event.confirmPassword,
                    errorMessage = null
                )
            }

            is LoginEvent.FirstNameChanged -> {
                _state.value = _state.value.copy(
                    firstName = event.firstName,
                    errorMessage = null
                )
            }

            is LoginEvent.LastNameChanged -> {
                _state.value = _state.value.copy(
                    lastName = event.lastName,
                    errorMessage = null
                )
            }

            is LoginEvent.AgeChanged -> {
                _state.value = _state.value.copy(
                    age = event.age.filter { it.isDigit() },
                    errorMessage = null
                )
            }

            is LoginEvent.CurrentInsulinChanged -> {
                _state.value = _state.value.copy(
                    currentInsulin = event.insulin,
                    errorMessage = null
                )
            }

            is LoginEvent.AddInsulin -> {
                val currentState = _state.value
                if (currentState.currentInsulin.isNotBlank() &&
                    !currentState.insulins.contains(currentState.currentInsulin)
                ) {
                    _state.value = currentState.copy(
                        insulins = currentState.insulins + currentState.currentInsulin,
                        currentInsulin = "",
                        errorMessage = null
                    )
                }
            }

            is LoginEvent.RemoveInsulin -> {
                _state.value = _state.value.copy(
                    insulins = _state.value.insulins - event.insulin,
                    errorMessage = null
                )
            }

            is LoginEvent.ToggleMode -> {
                _state.value = _state.value.copy(
                    isLoginMode = !_state.value.isLoginMode,
                    errorMessage = null,
                    // Čistimo polja pri promeni moda
                    password = "",
                    confirmPassword = "",
                    firstName = "",
                    lastName = "",
                    age = "",
                    insulins = emptyList(),
                    currentInsulin = ""
                )
            }

            is LoginEvent.Submit -> {
                submitForm()
            }
        }
    }
}