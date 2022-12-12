package rs.etf.running.ui.stateholders

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import rs.etf.running.data.room.Workout
import rs.etf.running.data.room.WorkoutRepository
import java.util.*
import javax.inject.Inject

@Parcelize
data class WorkoutCreateUiState(
    val date: Date? = null,
    val label: String = "",
    val distance: Double? = null,
    val duration: Double? = null,
) : Parcelable

@Parcelize
data class WorkoutListUiState(
    val isSorted: Boolean = false,
) : Parcelable

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val workoutRepository: WorkoutRepository,
) : ViewModel() {
    private val _uiStateCreate =
        savedStateHandle.getStateFlow("uiStateCreate", WorkoutCreateUiState())
    val uiStateCreate = _uiStateCreate

    fun setDate(y: Int, m: Int, d: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(y, m, d)
        savedStateHandle["uiStateCreate"] = _uiStateCreate.value.copy(date = calendar.time)
    }

    fun setLabel(label: String) {
        savedStateHandle["uiStateCreate"] = _uiStateCreate.value.copy(label = label)
    }

    fun setDistance(distanceAsString: String) {
        val distance = if (distanceAsString == "") null else distanceAsString.toDoubleOrNull()
        savedStateHandle["uiStateCreate"] = _uiStateCreate.value.copy(distance = distance)
    }

    fun setDuration(durationAsString: String) {
        val duration = if (durationAsString == "") null else durationAsString.toDoubleOrNull()
        savedStateHandle["uiStateCreate"] = _uiStateCreate.value.copy(duration = duration)
    }

    fun insertWorkout() = viewModelScope.launch {
        val date = _uiStateCreate.value.date
        val label = _uiStateCreate.value.label
        val distance = _uiStateCreate.value.distance
        val duration = _uiStateCreate.value.duration
        if (date != null && label != "" && distance != null && duration != null) {
            val workout = Workout(0, date, label, distance, duration)
            workoutRepository.insert(workout)
            savedStateHandle["uiStateCreate"] = WorkoutCreateUiState()
        }
    }

}