package rs.etf.mojdijabetes.ui.viewmodel

import androidx.activity.result.launch
import androidx.compose.animation.core.copy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rs.etf.mojdijabetes.data.local.entity.InsulinEntryEntity
import rs.etf.mojdijabetes.data.local.entity.UserInsulinEntity
import rs.etf.mojdijabetes.data.repository.AuthRepository
import rs.etf.mojdijabetes.data.repository.InsulinRepository
import javax.inject.Inject

@HiltViewModel
class AddInsulinViewModel @Inject constructor(
    private val insulinRepository: InsulinRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AddInsulinEntryState())
    val state: StateFlow<AddInsulinEntryState> = _state.asStateFlow()

    fun onInsulinNameChange(insulinName: String) {
        _state.update { it.copy(insulinName = insulinName) }
    }

    fun onInsulinAmountChange(insulinAmount: Int) {
        _state.update { it.copy(insulinAmount = insulinAmount) }
    }

    fun onDateTimeChange(dateTime: Long) {
        _state.update { it.copy(dateTime = dateTime) }
    }

    fun saveInsulinEntry(userEmail: String) {
        viewModelScope.launch {
            val entry = InsulinEntryEntity(
                insulinName = state.value.insulinName,
                insulinAmount = state.value.insulinAmount,
                dateTime = state.value.dateTime,
                userEmail = userEmail
            )
            insulinRepository.insertInsulinEntry(entry)
            _state.update { it.copy(isSaved = true) }
        }
    }

    fun resetIsSaved() {
        _state.update { it.copy(isSaved = false) }
    }
}

data class AddInsulinEntryState(
    val insulinName: String = "",
    val insulinAmount: Int = 0,
    val dateTime: Long = System.currentTimeMillis(),
    val isSaved: Boolean = false
)