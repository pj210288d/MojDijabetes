package rs.etf.mojdijabetes.data.model

import rs.etf.mojdijabetes.data.local.entity.UserEntity

data class User (
    val email: String,
    val password : String,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val insulins: List<String>,
    val createdAt: Long = System.currentTimeMillis()
)
fun User.toEntity() = UserEntity(
    email = email,
    password = password,
    firstName = firstName,
    lastName = lastName,
    age = age,
    createdAt = createdAt
)

fun UserEntity.toModel(insulins: List<String> = emptyList()) = User(
    email = email,
    password = password,
    firstName = firstName,
    lastName = lastName,
    age = age,
    insulins = insulins,
    createdAt = createdAt
)