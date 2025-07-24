package rs.etf.mojdijabetes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import rs.etf.mojdijabetes.data.local.entity.UserEntity
import rs.etf.mojdijabetes.data.local.entity.UserInsulinEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Transaction
    suspend fun insertUserWithInsulins(user: UserEntity, insulins: List<String>) {
        insertUser(user)
        insulins.forEach { insulin ->
            insertUserInsulin(UserInsulinEntity(userEmail = user.email, insulinName = insulin))
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInsulin(insulin: UserInsulinEntity)

    @Query("SELECT insulinName FROM user_insulins WHERE userEmail = :email")
    fun getUserInsulins(email: String): Flow<List<String>>
}