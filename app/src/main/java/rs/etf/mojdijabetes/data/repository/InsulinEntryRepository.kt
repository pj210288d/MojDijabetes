package rs.etf.mojdijabetes.data.repository

import kotlinx.coroutines.flow.Flow
import rs.etf.mojdijabetes.data.local.dao.InsulinDao
import rs.etf.mojdijabetes.data.local.entity.InsulinEntryEntity
import javax.inject.Inject

class InsulinRepository @Inject constructor(
    private val insulinDao: InsulinDao
) {
    // ... other code ...
    fun getInsulinEntries(userEmail: String): Flow<List<InsulinEntryEntity>> {
        return insulinDao.getInsulinEntries(userEmail)
    }
    suspend fun insertInsulinEntry(insulinEntryEntity: InsulinEntryEntity) {
        insulinDao.insertInsulinEntry(insulinEntryEntity)
    }
}