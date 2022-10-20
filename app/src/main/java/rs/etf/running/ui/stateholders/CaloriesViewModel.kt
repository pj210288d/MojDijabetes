package rs.etf.running.ui.stateholders

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

// https://kotlinlang.org/docs/data-classes.html
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
)

// https://developer.android.com/topic/libraries/architecture/viewmodel
class CaloriesViewModel : ViewModel() {
    // https://developer.android.com/kotlin/flow
    private val _uiState = MutableStateFlow(CaloriesUiState())
    val uiState = _uiState

    fun setWeight(weight: String) {
        _uiState.update { currentCaloriesUiState ->
            // https://kotlinlang.org/docs/data-classes.html#copying
            currentCaloriesUiState.copy(weight = weight)
        }
    }

    fun setHeight(height: String) {
        _uiState.update { it.copy(height = height) }
    }

    fun setAge(age: String) {
        _uiState.update { it.copy(age = age) }
    }

    fun setIsMale() {
        _uiState.update { it.copy(isMale = true, isFemale = false) }
    }

    fun setIsFemale() {
        _uiState.update { it.copy(isFemale = true, isMale = false) }
    }

    fun setDuration(duration: String) {
        _uiState.update { it.copy(duration = duration) }
    }

    fun setMetIndex(metIndex: Int) {
        _uiState.update { it.copy(metIndex = metIndex) }
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

            _uiState.update {
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