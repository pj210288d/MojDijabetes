package rs.etf.mojdijabetes.ui.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.copy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rs.etf.mojdijabetes.data.local.entity.GlucoseEntryEntity
import rs.etf.mojdijabetes.data.repository.GlucoseEntryRepository
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.text.toFloat


import java.time.ZoneOffset

@HiltViewModel
class AddGlucoseViewModel @Inject constructor(
    private val glucoseEntryRepository: GlucoseEntryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AddGlucoseState())
    val state: StateFlow<AddGlucoseState> = _state.asStateFlow()

    fun onValueChange(value: String) {
        _state.value = _state.value.copy(value = value)
    }

    fun onDateTimeChange(dateTime: Long) {
        _state.value = _state.value.copy(dateTime = dateTime)
    }

    fun onInsulinNameChange(insulinName: String) {
        _state.value = _state.value.copy(insulinName = insulinName)
    }

    fun onMealChange(meal: String) {
        _state.value = _state.value.copy(meal = meal)
    }
    fun onInsulinAmountChange(insulinAmount: Int) {
        _state.update{it.copy(insulinAmount = insulinAmount)}
    }
    fun saveGlucoseEntry(userEmail: String) {
        viewModelScope.launch {
            val glucoseEntry = GlucoseEntryEntity(
                userEmail = userEmail,
                value = _state.value.value.toFloat(),
                dateTime = _state.value.dateTime,
                insulinName = if (_state.value.insulinName == "Bez insulina") null else _state.value.insulinName,
                meal = _state.value.meal,
                insulinAmount = _state.value.insulinAmount
            )
            glucoseEntryRepository.insertGlucoseEntry(glucoseEntry)
            _state.value = _state.value.copy(isSaved = true)
        }
    }
    fun resetIsSaved() {
        _state.value = _state.value.copy(isSaved = false)
    }
    fun resetState() {
        _state.update { AddGlucoseState() }
    }
}

