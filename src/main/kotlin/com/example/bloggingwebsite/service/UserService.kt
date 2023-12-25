package com.example.bloggingwebsite.service

import com.example.bloggingwebsite.*
import com.example.bloggingwebsite.sucurity.JwtService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

interface UserService {
    fun authentication(request: AuthenticationRequest): AuthenticationResponse
    fun register(request: RegisterRequest): BaseResponse<Any>
    fun getUsers(page: Int, size: Int): Page<User>
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val roleRepository: RoleRepository,
    private val userSession: UserSession,
) : UserService {
    override fun authentication(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )
        val user = userRepository.findByEmail(request.username).orElseThrow()
        val accessToken = jwtService.generateToken(UserManager(user))
        val refreshToken = jwtService.generateRefreshToken(UserManager(user))
        return AuthenticationResponse(accessToken, refreshToken)
    }

    override fun register(request: RegisterRequest): BaseResponse<Any> {
        if (!userRepository.existsByEmail(request.username)) {
            val roleUser = roleRepository.findByRoleName(RoleName.ROLE_USER)

            if (request.password == request.confirmPassword) {
                val user = User(
                    request.firstname,
                    request.lastname,
                    request.username,
                    passwordEncoder.encode(request.password),
                    Status.CONFIRM,
                    setOf(roleUser)
                )

                val accessToken = jwtService.generateToken(UserManager(user))
                val refreshToken = jwtService.generateRefreshToken(UserManager(user))

                return BaseResponse(
                    false, buildString {
                        append("accessToken : $accessToken\n")
                        append("refreshToken : $refreshToken")
                    }
                )
            }
            return BaseResponse(true, "PASSWORD NOT MATCHED")
        }

        return BaseResponse(true, "INTERNAL SERVER ERROR")
    }

    override fun getUsers(page: Int, size: Int): Page<User> =
        userRepository.findAllNotDeleted(PageRequest.of(page - 1, size))

}



