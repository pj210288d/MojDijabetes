package rs.etf.running.data.room

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepository @Inject constructor(private val workoutDao: WorkoutDao) {

    val allWorkouts = workoutDao.getAllAsFlow()

    suspend fun insert(workout: Workout) {
        workoutDao.insert(workout)
    }
}