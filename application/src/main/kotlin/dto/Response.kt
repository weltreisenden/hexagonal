package dto

data class Response (
    val token: String,
    val username: String,
    val email: String,
    val role: String
)

