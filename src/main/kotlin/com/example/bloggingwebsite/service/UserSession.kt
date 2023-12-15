package com.example.bloggingwebsite.service

import com.example.bloggingwebsite.User
import com.example.bloggingwebsite.UserRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*


@Component
class UserSession(
    private val userRepository: UserRepository
) {

    fun getUsername(): String? {
        return getPrincipal()
            .map { user -> user.principal.toString() }
            .orElse(null)
    }

    fun getUser(): User? {
        return getUsername()?.let { userRepository.findByEmail(it).orElse(null) }
    }

    fun getPrincipal(): Optional<UsernamePasswordAuthenticationToken> {
        val principal = SecurityContextHolder.getContext().authentication
        return if (principal is UsernamePasswordAuthenticationToken) {
            Optional.of(principal)
        } else {
            Optional.empty()
        }
    }

}