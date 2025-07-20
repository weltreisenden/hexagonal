package com.example.board.application.port.inbound

import com.example.board.domain.UserToken

interface AuthInboundPort {
    fun register(username: String, email: String, password: String): UserToken
    fun login(username: String, password: String): UserToken
    fun validateToken(token: String): Boolean
}