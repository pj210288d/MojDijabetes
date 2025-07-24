package rs.etf.mojdijabetes.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "insulin_entries")
data class InsulinEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val insulinName: String,
    val insulinAmount: Int,
    val dateTime: Long,
    val userEmail: String
)