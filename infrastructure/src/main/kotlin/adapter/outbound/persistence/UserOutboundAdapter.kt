package adapter.outbound.persistence

import model.User
import port.outbound.UserOutboundPort
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
                role = user.role,
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
                role = user.role
                createdAt = user.createdAt
                updatedAt = user.updatedAt
            }
            existingEntity
        }

        val savedEntity = userJpaRepository.save(entity)
        return savedEntity.toDomain()
    }

    override fun findById(id: Long): User? {
        return userJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findByUsername(username: String): User? {
        return userJpaRepository.findByUsername(username)?.toDomain()
    }

    override fun findByEmail(email: String): User? {
        return userJpaRepository.findByEmail(email)?.toDomain()
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

fun UserEntity.toDomain(): User {
    return User(
        id = this.id,
        username = this.username,
        email = this.email,
        password = this.password,
        role = this.role,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
