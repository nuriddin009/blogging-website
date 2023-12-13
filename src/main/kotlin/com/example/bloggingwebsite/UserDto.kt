package com.example.bloggingwebsite

import jakarta.validation.constraints.NotBlank
import java.io.Serializable

/**
 * DTO for {@link com.example.bloggingwebsite.User}
 */
data class UserDto(
    val firstname: String? = null,
    val lastname: String? = null,
    val username: String? = null,
    @field:NotBlank val password: String? = null,
) : Serializable