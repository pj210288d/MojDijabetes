package rs.etf.mojdijabetes.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

data class AddGlucoseState (
    val value: String = "",
    val insulinName: String = "",
    val meal: String = "",
    val dateTime: Long = System.currentTimeMillis(),
    val isSaved: Boolean = false,
    val insulinAmount: Int = 0
)