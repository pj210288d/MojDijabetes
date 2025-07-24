package rs.etf.mojdijabetes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import rs.etf.mojdijabetes.data.local.entity.InsulinEntryEntity
import rs.etf.mojdijabetes.data.local.entity.UserInsulinEntity

@Dao
interface InsulinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInsulin(insulinEntity: UserInsulinEntity)

    @Query("SELECT * FROM insulin_entries WHERE userEmail = :userEmail")
    fun getInsulinEntries(userEmail: String): Flow<List<InsulinEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInsulinEntry(insulinEntryEntity: InsulinEntryEntity)
}