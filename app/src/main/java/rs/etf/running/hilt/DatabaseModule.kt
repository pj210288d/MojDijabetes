package rs.etf.running.hilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rs.etf.running.data.room.RunningDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context) =
        RunningDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun providesWorkoutDao(database: RunningDatabase) =
        database.workoutDao()
}