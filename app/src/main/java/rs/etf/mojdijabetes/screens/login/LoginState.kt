package rs.etf.mojdijabetes.screens.login


data class LoginState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val age: String = "",
    val insulins: List<String> = emptyList(),
    val currentInsulin: String = "",
    val isLoginMode: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isAuthenticated: Boolean = false

)

// Događaji koje korisnik može da izazove na ekranu
sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : LoginEvent()
    data class FirstNameChanged(val firstName: String) : LoginEvent()
    data class LastNameChanged(val lastName: String) : LoginEvent()
    data class AgeChanged(val age: String) : LoginEvent()
    data class CurrentInsulinChanged(val insulin: String) : LoginEvent()
    object AddInsulin : LoginEvent()
    data class RemoveInsulin(val insulin: String) : LoginEvent()
    object ToggleMode : LoginEvent()
    object Submit : LoginEvent()
}