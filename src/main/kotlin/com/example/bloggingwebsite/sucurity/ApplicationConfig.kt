package com.example.bloggingwebsite.sucurity

import com.example.bloggingwebsite.Status
import com.example.bloggingwebsite.User
import com.example.bloggingwebsite.UserManager
import com.example.bloggingwebsite.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import java.util.*


@Configuration
class ApplicationConfig(
    private val userRepository: UserRepository,
) {


    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            val user = userRepository.findByUsername(username)
                .orElseThrow { EntityNotFoundException("User not found") }
            if (user.status == Status.ACTIVE)
                user.let(::UserManager)
            else throw EntityNotFoundException("User not active")
        }
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }


    fun getUsername(): String? {
        return getPrincipal()
            .map { user -> user.principal.toString() }
            .orElse(null)
    }

    fun getUser(): User? {
        return getUsername()?.let { userRepository.findByUsername(it).orElse(null) }
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

