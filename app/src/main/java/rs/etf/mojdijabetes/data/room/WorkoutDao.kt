//package rs.etf.mojdijabetes.data.room
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface WorkoutDao {
//
//    @Insert
//    suspend fun insert(workout: Workout)
//
//    @Query("SELECT * FROM Workout")
//    suspend fun getAll(): List<Workout>
//
//    @Query("SELECT * FROM Workout")
//    fun getAllAsFlow(): Flow<List<Workout>>
//
//    @Query("SELECT * FROM Workout ORDER BY distance DESC")
//    fun getAllSortedAsFlow(): Flow<List<Workout>>
//}