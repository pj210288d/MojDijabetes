package rs.etf.mojdijabetes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import rs.etf.mojdijabetes.data.local.dao.GlucoseDao
import rs.etf.mojdijabetes.data.local.dao.InsulinDao
import rs.etf.mojdijabetes.data.local.dao.UserDao
import rs.etf.mojdijabetes.data.local.entity.GlucoseEntryEntity
import rs.etf.mojdijabetes.data.local.entity.InsulinEntryEntity
import rs.etf.mojdijabetes.data.local.entity.UserEntity
import rs.etf.mojdijabetes.data.local.entity.UserInsulinEntity
import rs.etf.mojdijabetes.hilt.DatabaseModule

@Database(
    entities = [
        UserEntity::class,
        UserInsulinEntity::class,
        GlucoseEntryEntity::class,
        InsulinEntryEntity::class
    ],
    version = 7
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun glucoseDao(): GlucoseDao
    abstract fun insulinDao(): InsulinDao
}