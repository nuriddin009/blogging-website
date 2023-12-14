package com.example.bloggingwebsite

import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataLoader(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val roleRepository: RoleRepository,
) : CommandLineRunner {
    override fun run(vararg args: String?) {


        if (userRepository.count() == 0L) {
            val roleUser = roleRepository.save(Role(RoleName.ROLE_USER))
            val roleAdmin = roleRepository.save(Role(RoleName.ROLE_ADMIN))
            userRepository.save(
                User(
                    "Nuriddin",
                    "Inoyatov",
                    "nuriddin",
                    passwordEncoder.encode("nuriddin"),
                    Status.ACTIVE,
                    setOf(roleUser, roleAdmin)
                )
            )
        }


    }
}