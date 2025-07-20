package com.example.board.adapter.inbound.controller

import com.example.board.adapter.inbound.controller.dto.Response
import com.example.board.application.exception.AuthenticationException
import com.example.board.application.exception.UserAlreadyExistsException
import com.example.board.application.port.inbound.AuthInboundPort
import com.example.board.domain.UserToken
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthInboundAdapter(
    private val authInboundPort: AuthInboundPort
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<AuthResponseDto> {
        return try {
            val authResponse = authInboundPort.register(
                username = request.username,
                email = request.email,
                password = request.password
            )
            ResponseEntity.status(HttpStatus.CREATED).body(authResponse.toDto())
        } catch (e: UserAlreadyExistsException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<AuthResponseDto> {
        return try {
            val authResponse = authInboundPort.login(
                username = request.username,
                password = request.password
            )
            ResponseEntity.ok(authResponse.toDto())
        } catch (e: AuthenticationException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @PostMapping("/validate")
    fun validateToken(@Valid @RequestBody request: TokenValidationRequest) : ResponseEntity<TokenValidationResponse> {
        val isValid = authInboundPort.validateToken(request.token)
        return ResponseEntity.ok(TokenValidationResponse(isValid))
    }

}

data class RegisterRequest(
    @field:NotBlank(message = "사용자명은 필수입니다")
    @field:Size(min = 3, max = 20, message = "사용자명은 3-20자 사이여야 합니다")
    val username: String,

    @field:NotBlank(message = "이메일은 필수입니다")
    @field:Email(message = "올바른 이메일 형식이 아닙니다")
    val email: String,

    @field:NotBlank(message = "비밀번호는 필수입니다")
    @field:Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다")
    val password: String
)

data class LoginRequest(
    @field:NotBlank(message = "사용자명은 필수입니다")
    val username: String,

    @field:NotBlank(message = "비밀번호는 필수입니다")
    val password: String
)

data class TokenValidationRequest(
    @field:NotBlank(message = "토큰은 필수입니다")
    val token: String
)

data class AuthResponseDto(
    val token: String,
    val username: String,
    val email: String,
    val role: String
)

data class TokenValidationResponse(
    val isValid: Boolean
)

fun UserToken.toDto(): AuthResponseDto {
    return AuthResponseDto(
        token = this.token,
        username = this.username,
        email = this.email,
        role = this.role
    )
}