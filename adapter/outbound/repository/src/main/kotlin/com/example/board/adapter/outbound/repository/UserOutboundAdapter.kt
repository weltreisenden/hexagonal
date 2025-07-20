package com.example.board.adapter.outbound.repository

import com.example.board.domain.User
import com.example.board.application.port.outbound.UserOutboundPort
import com.example.board.infrastructure.jpa.UserEntity
import com.example.board.infrastructure.jpa.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserOutboundAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserOutboundPort {

    override fun save(user: User): User {
        val entity = if (user.id == null) {
            UserEntity(
                username = user.username,
                email = user.email,
                password = user.password,
                role = user.role.toString(),
                createdAt = user.createdAt,
                updatedAt = user.updatedAt
            )
        } else {
            val existingEntity = userJpaRepository.findById(user.id!!)
                .orElseThrow { IllegalArgumentException("User not found with id: ${user.id}") }

            existingEntity.apply {
                username = user.username
                email = user.email
                password = user.password
                role = user.role.toString()
                createdAt = user.createdAt
                updatedAt = user.updatedAt
            }
            existingEntity
        }

        val savedEntity = userJpaRepository.save(entity)
        return savedEntity.let{ UserMapper.mapEntityToDomain(it) }
    }

    override fun findById(id: Long): User? {
        return userJpaRepository.findByIdOrNull(id)?.let{ UserMapper.mapEntityToDomain(it) }
    }

    override fun findByUsername(username: String): User? {
        return userJpaRepository.findByUsername(username)?.let{ UserMapper.mapEntityToDomain(it) }
    }

    override fun findByEmail(email: String): User? {
        return userJpaRepository.findByEmail(email)?.let{ UserMapper.mapEntityToDomain(it) }
    }

/*    override fun existsByUsername(username: String): Boolean {
        return userJpaRepository.existsByUsername(username)
    }*/

    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email)
    }

    override fun deleteById(id: Long) {
        userJpaRepository.deleteById(id)
    }
}

