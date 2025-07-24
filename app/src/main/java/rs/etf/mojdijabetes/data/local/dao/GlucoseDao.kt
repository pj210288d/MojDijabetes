package rs.etf.mojdijabetes.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import rs.etf.mojdijabetes.data.local.entity.GlucoseEntryEntity
import java.time.LocalDateTime

@Dao
interface GlucoseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGlucoseEntry(glucoseEntry: GlucoseEntryEntity)

    @Query("SELECT * FROM glucose_entries WHERE userEmail = :userEmail ORDER BY dateTime DESC")
    fun getGlucoseEntries(userEmail: String): Flow<List<GlucoseEntryEntity>>

    @Query("SELECT * FROM glucose_entries WHERE userEmail = :userEmail AND DATE(dateTime / 1000, 'unixepoch') = DATE(:date / 1000, 'unixepoch') ORDER BY dateTime DESC")
    fun getGlucoseEntriesByDate(userEmail: String, date: Long): Flow<List<GlucoseEntryEntity>>

    @Delete
    suspend fun deleteGlucoseEntry(glucoseEntry: GlucoseEntryEntity)
}