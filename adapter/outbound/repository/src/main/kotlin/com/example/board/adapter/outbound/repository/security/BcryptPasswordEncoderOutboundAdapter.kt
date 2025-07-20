package com.example.board.adapter.outbound.repository.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import com.example.board.application.port.outbound.PasswordEncoderOutboundPort

// todo 밖에서 처리하는걸로 뺴기?
@Component
class BcryptPasswordEncoderOutboundAdapter : PasswordEncoderOutboundPort {

    private val encoder = BCryptPasswordEncoder()

    override fun encode(rawPassword: String) : String{
        return encoder.encode(rawPassword)
    }

    override fun matches(rawPassword: String, encoderPassword: String): Boolean {
        return encoder.matches(rawPassword, encoderPassword)
    }
}