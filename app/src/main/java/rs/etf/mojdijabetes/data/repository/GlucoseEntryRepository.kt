package rs.etf.mojdijabetes.data.repository

import kotlinx.coroutines.flow.Flow
import rs.etf.mojdijabetes.data.local.dao.GlucoseDao
import rs.etf.mojdijabetes.data.local.entity.GlucoseEntryEntity
import javax.inject.Inject

class GlucoseEntryRepository @Inject constructor(
    private val glucoseDao: GlucoseDao
) {
    suspend fun insertGlucoseEntry(glucoseEntry: GlucoseEntryEntity) {
        glucoseDao.insertGlucoseEntry(glucoseEntry)
    }

    fun getGlucoseEntries(userEmail: String): Flow<List<GlucoseEntryEntity>> {
        return glucoseDao.getGlucoseEntries(userEmail)
    }

    fun getGlucoseEntriesByDate(userEmail: String, date: Long): Flow<List<GlucoseEntryEntity>> {
        return glucoseDao.getGlucoseEntriesByDate(userEmail, date)
    }

    suspend fun deleteGlucoseEntry(glucoseEntry: GlucoseEntryEntity) {
        glucoseDao.deleteGlucoseEntry(glucoseEntry)
    }
}