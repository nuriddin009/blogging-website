package com.example.bloggingwebsite.annotations

import com.example.bloggingwebsite.User
import com.example.bloggingwebsite.UserRepository
import jakarta.validation.constraints.NotNull
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component("securityAuditorAware")
class SecurityAuditorAware(
    private val userRepository: UserRepository,
) : AuditorAware<User> {
    @NotNull
    override fun getCurrentAuditor(): Optional<User> {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication != null && authentication.isAuthenticated && authentication.principal != null)
            userRepository.findByEmail(authentication.principal as String)
        else
            Optional.empty()
    }
}