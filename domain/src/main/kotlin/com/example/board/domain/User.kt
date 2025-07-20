package com.example.board.domain

import java.time.LocalDateTime

data class User(
    val id: Long? = null,
    val username: String,
    val email: String,
    val password: String,
    val role: UserRole = UserRole.USER,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {

    fun updatePassword(newPassword: String): User {
        return this.copy(
            password = newPassword,
            updatedAt = LocalDateTime.now()
        )
    }

    fun isAdmin(): Boolean = role == UserRole.ADMIN
}