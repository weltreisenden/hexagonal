package port.outbound

interface PasswordEncoderOutboundPort {
    fun encode(rawPassword: String): String
    fun matches(rawPassword: String, encoderPassword: String): Boolean
}