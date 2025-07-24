package rs.etf.mojdijabetes.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import rs.etf.mojdijabetes.data.local.UserPreferences
import rs.etf.mojdijabetes.data.local.entity.GlucoseEntryEntity
import rs.etf.mojdijabetes.data.local.entity.InsulinEntryEntity
import rs.etf.mojdijabetes.data.local.entity.UserEntity
import rs.etf.mojdijabetes.data.local.entity.UserInsulinEntity
import rs.etf.mojdijabetes.data.repository.AuthRepository
import rs.etf.mojdijabetes.data.repository.GlucoseEntryRepository
import rs.etf.mojdijabetes.domain.usecase.SignOutUseCase
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences,
    private val glucoseEntryRepository: GlucoseEntryRepository
) : ViewModel() {

    private val _userInsulins = MutableStateFlow<List<String>>(emptyList())
    val userInsulins: StateFlow<List<String>> = _userInsulins.asStateFlow()

    private val _glucoseEntries = MutableStateFlow<List<GlucoseEntry>>(emptyList())
    val glucoseEntries: StateFlow<List<GlucoseEntry>> = _glucoseEntries.asStateFlow()

    private val _selectedInsulin = MutableStateFlow("")
    val selectedInsulin: StateFlow<String> = _selectedInsulin.asStateFlow()

    val currentUserEmail: StateFlow<String?> = userPreferences.userEmail.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    init {
        viewModelScope.launch {
            // Get current user email
            currentUserEmail.collect { email ->
                Log.d("HomeViewModel", "Current user email: $email")
                email?.let {
                    // Check if user exists and insert if not
                    val user = authRepository.getUserByEmail(it)
                    if (user == null) {
                        authRepository.insertUser(UserEntity(it, "", "", "", 0))
                    }

                    // Fetch user insulins
                    authRepository.getUserInsulins(it).collect { insulins ->
                        Log.d("HomeViewModel", "Fetched insulins: $insulins")
                        _userInsulins.value = insulins
                        if (insulins.isNotEmpty() && _selectedInsulin.value.isEmpty()) {
                            _selectedInsulin.value = insulins.first()
                        }
                    }
                }
            }
        }
    }
    fun loadGlucoseEntries(userEmail: String) {
        viewModelScope.launch {
            glucoseEntryRepository.getGlucoseEntries(userEmail).collect { glucoseEntriesEntities ->
                val entries = glucoseEntriesEntities.map { it.toGlucoseEntry() }
                _glucoseEntries.value = entries
            }
        }
    }
//    fun loadUserInsulins(userEmail: String) {
//        viewModelScope.launch {
//            authRepository.getUserInsulins(userEmail).collect { insulins ->
//                _userInsulins.value = insulins.map { it.name }
//            }
//        }
//    }
    fun onInsulinSelected(insulin: String) {
        _selectedInsulin.value = insulin
    }

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
        }
    }
}

data class GlucoseEntry(
    val id: Int = 0,
    val value: String,
    val dateTime: Long,
    val insulinName: String,
    val insulinAmount: Int,
    val meal: String,
    val userEmail: String
)
fun GlucoseEntryEntity.toGlucoseEntry(): GlucoseEntry {
    return GlucoseEntry(
        id = id.toInt(),
        value = value.toString(),
        dateTime = dateTime,
        insulinName = insulinName.toString(),
        insulinAmount = insulinAmount,
        meal = meal,
        userEmail = userEmail
    )
}
data class InsulinEntry(
    val id: Int = 0,
    val insulinName: String,
    val insulinAmount: Int,
    val dateTime: Long,
    val userEmail: String
)
fun InsulinEntryEntity.toInsulinEntry(): InsulinEntry {
    return InsulinEntry(
        id = id,
        insulinName = insulinName,
        insulinAmount = insulinAmount,
        dateTime = dateTime,
        userEmail = userEmail
    )
}
data class Insulin(
    val id: Int = 0,
    val name: String,
    val userEmail: String
)
fun UserInsulinEntity.toInsulin(): Insulin {
    return Insulin(
        id = id.toInt(),
        name = insulinName,
        userEmail = userEmail
    )
}