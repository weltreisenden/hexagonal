package adapter.outbound.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import port.outbound.PasswordEncoderOutboundPort

@Component
class BcryptPasswordEncoderOutboundAdapter : PasswordEncoderOutboundPort{

    private val encoder = BCryptPasswordEncoder()

    override fun encode(rawPassword: String) : String{
        return encoder.encode(rawPassword)
    }

    override fun matches(rawPassword: String, encoderPassword: String): Boolean {
        return encoder.matches(rawPassword, encoderPassword)
    }
}