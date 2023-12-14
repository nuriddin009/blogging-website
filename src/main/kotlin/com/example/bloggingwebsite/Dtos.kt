package com.example.bloggingwebsite

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime


data class UserManager(var user: User) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return user.roles.stream().map { SimpleGrantedAuthority(it.roleName.name) }.toList()
    }

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}


data class AuthenticationRequest(
    val username: String,
    val password: String,
)

data class RegisterRequest(
    val username: String,
    val password: String,
    val confirmPassword: String,
    val firstname: String,
    val lastname: String,

    )

@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder("message", "localDateTime", "responseData", "responseDataList")
@JsonInclude(JsonInclude.Include.NON_NULL)
class BaseResponse<T> {
    var error: Boolean = false
    var message: String = "Success"
    var timeStamp: LocalDateTime = LocalDateTime.now()
    var responseData: T? = null
    var responseDataList: Collection<T>? = null

    constructor(error: Boolean, message: String) {
        this.error = error
        this.message = message
    }
}


data class AuthenticationResponse(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("refresh_token") val refreshToken: String,
)