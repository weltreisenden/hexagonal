package port.outbound

interface TokenProviderPort {
    fun generateToken(username: String, role: String): String
    fun validateToken(token: String): Boolean
    fun getUsername(token: String): String
    fun getRole(token: String): String
}