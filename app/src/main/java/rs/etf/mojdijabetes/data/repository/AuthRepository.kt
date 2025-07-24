package rs.etf.mojdijabetes.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import rs.etf.mojdijabetes.data.local.UserPreferences
import rs.etf.mojdijabetes.data.local.dao.InsulinDao
import rs.etf.mojdijabetes.data.local.dao.UserDao
import rs.etf.mojdijabetes.data.local.entity.UserEntity
import rs.etf.mojdijabetes.data.local.entity.UserInsulinEntity
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val userDao: UserDao,
    private val userPreferences: UserPreferences,
//    private val insulinDao: InsulinDao
) {
    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }

    fun getUserInsulins(email: String): Flow<List<String>> {
        return userDao.getUserInsulins(email)
    }

    suspend fun insertUserInsulin(userInsulin: UserInsulinEntity) {
        userDao.insertUserInsulin(userInsulin)
    }

    suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        age: Int,
        insulins: List<String>
    ) {
        userPreferences.setLoggedIn(email)
        userDao.insertUser(UserEntity(email, password, firstName, lastName, age))
        insulins.forEach { insulin ->
            userDao.insertUserInsulin(UserInsulinEntity(userEmail = email, insulinName = insulin))
        }
    }

    suspend fun login(email: String, password: String): Result<Unit> {
        val user = userDao.getUserByEmail(email)
        return if (user != null && user.password == password) {
            userPreferences.setLoggedIn(email)
            Result.success(Unit)
        } else {
            Result.failure(Exception("Pogrešna email adresa ili lozinka"))
        }
    }

    val isUserLoggedIn: Flow<Boolean> = userPreferences.isLoggedIn
}