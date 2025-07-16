package inbound

import dto.Response

interface AuthInboundPort {
    fun register(username: String, email: String, password: String): Response
    fun login(username: String, password: String): Response
    fun validateToken(token: String): Boolean
}