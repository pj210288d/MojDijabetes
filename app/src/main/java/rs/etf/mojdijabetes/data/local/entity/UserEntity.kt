package rs.etf.mojdijabetes.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val createdAt: Long = System.currentTimeMillis()
)