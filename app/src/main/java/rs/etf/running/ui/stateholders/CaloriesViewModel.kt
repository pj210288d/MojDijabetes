package rs.etf.running.ui.stateholders

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

// https://developer.android.com/kotlin/parcelize
@Parcelize
data class CaloriesUiState(
    val weight: String = "",
    val height: String = "",
    val age: String = "",
    val isMale: Boolean = false,
    val isFemale: Boolean = false,
    val duration: String = "",
    val metIndex: Int = 0,
    val caloriesNeeded: Double? = null,
    val caloriesBurned: Double? = null,
) : Parcelable

const val UI_STATE_KEY = "uiState"

// $ adb devices -l
// $ adb -t <transport-id> shell am kill <package-name>

// https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-savedstate
@HiltViewModel
class CaloriesViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) :
    ViewModel() {
    private val _uiState1 = MutableStateFlow(CaloriesUiState())

    // https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-savedstate#savedstate-stateflow
    private val _uiState2 = savedStateHandle.getStateFlow(UI_STATE_KEY, CaloriesUiState())

    val uiState = _uiState2

    fun setWeight(weight: String) {
        savedStateHandle[UI_STATE_KEY] = _uiState2.value.copy(weight = weight)
        _uiState1.update { currentCaloriesUiState -> currentCaloriesUiState.copy(weight = weight) }
    }

    fun setHeight(height: String) {
        savedStateHandle[UI_STATE_KEY] = _uiState2.value.copy(height = height)
        _uiState1.update { it.copy(height = height) }
    }

    fun setAge(age: String) {
        savedStateHandle[UI_STATE_KEY] = _uiState2.value.copy(age = age)
        _uiState1.update { it.copy(age = age) }
    }

    fun setIsMale() {
        savedStateHandle[UI_STATE_KEY] = _uiState2.value.copy(isMale = true, isFemale = false)
        _uiState1.update { it.copy(isMale = true, isFemale = false) }
    }

    fun setIsFemale() {
        savedStateHandle[UI_STATE_KEY] = _uiState2.value.copy(isFemale = true, isMale = false)
        _uiState1.update { it.copy(isFemale = true, isMale = false) }
    }

    fun setDuration(duration: String) {
        savedStateHandle[UI_STATE_KEY] = _uiState2.value.copy(duration = duration)
        _uiState1.update { it.copy(duration = duration) }
    }

    fun setMetIndex(metIndex: Int) {
        savedStateHandle[UI_STATE_KEY] = _uiState2.value.copy(metIndex = metIndex)
        _uiState1.update { it.copy(metIndex = metIndex) }
    }

    fun calculateCalories(): Boolean {
        try {
            val weight = uiState.value.weight.toDouble()
            val height = uiState.value.height.toDouble()
            val age = uiState.value.age.toDouble()
            val duration = uiState.value.duration.toDouble()

            val metValues = arrayOf(6.0, 8.3, 9.0, 9.4, 9.8, 10.5, 11.5, 11.8, 12.8)
            val metValue = metValues[uiState.value.metIndex]

            val caloriesNeeded = if (uiState.value.isMale)
                66 + 13.7 * weight + 5 * height - 6.8 * age
            else
                655.1 + 9.6 * weight + 1.9 * height - 4.7 * age
            val caloriesBurned = duration * metValue * 3.5 * weight / 200

            savedStateHandle[UI_STATE_KEY] = _uiState2.value.copy(
                caloriesNeeded = caloriesNeeded,
                caloriesBurned = caloriesBurned
            )
            _uiState1.update {
                it.copy(
                    caloriesNeeded = caloriesNeeded,
                    caloriesBurned = caloriesBurned
                )
            }

            return true
        } catch (e: Exception) {
            return false
        }
    }
}