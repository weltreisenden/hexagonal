package service

import dto.Response
import exception.AuthenticationException
import exception.UserAlreadyExistsException
import exception.UserNotFoundException
import inbound.AuthInboundPort
import model.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import port.outbound.PasswordEncoderOutboundPort
import port.outbound.TokenProviderPort
import port.outbound.UserOutboundPort

@Service
@Transactional
class AuthService (
    private val userOutboundPort: UserOutboundPort,
    private val passwordEncoderOutboundPort: PasswordEncoderOutboundPort,
    private val tokenProviderOutboundPort: TokenProviderPort
) : AuthInboundPort {

    override fun register(username: String, email: String, password: String): Response {

        if (userOutboundPort.existsByEmail(username)) {
            throw UserAlreadyExistsException("이미 존재하는 사용자명입니다: $username")
        }

        if (userOutboundPort.existsByEmail(email)) {
            throw UserAlreadyExistsException("이미 존재하는 이메일입니다: $email")
        }

        val encodedPassword = passwordEncoderOutboundPort.encode(password)
        val user = User(
            username = username,
            email = email,
            password = encodedPassword
        )

        val savedUser = userOutboundPort.save(user)
        val token = tokenProviderOutboundPort.generateToken(savedUser.username, savedUser.role.name)

        return Response(
            token = token,
            username = savedUser.username,
            email = savedUser.email,
            role = savedUser.role.name
        )
    }

    override fun login(username: String, password: String): Response {
        val user = userOutboundPort.findByUsername(username)?: throw AuthenticationException("사용자를 찾을 수 없습니다: $username")

        if (!passwordEncoderOutboundPort.matches(password, user.password)) {
            throw AuthenticationException("잘못된 비밀번호입니다")
        }

        val token = tokenProviderOutboundPort.generateToken(user.username, user.role.name)

        return Response(
            token = token,
            username = user.username,
            email = user.email,
            role = user.role.name
        )
    }

    override fun validateToken(token: String): Boolean {
        return tokenProviderOutboundPort.validateToken(token)
    }

    @Transactional(readOnly = true)
    fun getUserFromToken(token: String): User {
        if (!tokenProviderOutboundPort.validateToken(token)) {
            throw AuthenticationException("유효하지 않은 토큰입니다")
        }

        val username = tokenProviderOutboundPort.getUsername(token)
        return userOutboundPort.findByUsername(username)
            ?: throw UserNotFoundException("사용자를 찾을 수 없습니다 $username")

    }


}