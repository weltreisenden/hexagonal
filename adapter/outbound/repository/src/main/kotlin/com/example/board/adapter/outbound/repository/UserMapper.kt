package com.example.board.adapter.outbound.repository

import com.example.board.domain.User
import com.example.board.domain.UserRole
import com.example.board.infrastructure.jpa.UserEntity

object UserMapper {

    fun mapEntityToDomain(entity: UserEntity): User {
        return with(entity) {
            User(
                id = this.id,
                username = this.username,
                email = this.email,
                password = this.password,
                role = UserRole.valueOf(this.role),
                createdAt = this.createdAt,
                updatedAt = this.updatedAt
            )
        }
    }
}