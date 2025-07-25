package port.outbound

import model.User

interface UserOutboundPort {
    fun save(user: User): User
    fun findById(id: Long): User?
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
    fun deleteById(id: Long)
}