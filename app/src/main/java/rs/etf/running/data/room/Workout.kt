package rs.etf.running.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Workout(
    @PrimaryKey(autoGenerate = true) var id: Long,
    val date: Date,
    val label: String,
    val distance: Double,
    val duration: Double,
)