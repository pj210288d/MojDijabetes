package rs.etf.running.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(DateConverter::class)
@Database(entities = [Workout::class], version = 1, exportSchema = false)
abstract class RunningDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao

    companion object {
        private var INSTANCE: RunningDatabase? = null

        fun getDatabase(context: Context): RunningDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RunningDatabase::class.java,
                    "running_database"
                ).build()
                INSTANCE = instance
                // return instance
                return@synchronized instance
            }
        }
    }
}
