package com.example.bloggingwebsite.service

import com.example.bloggingwebsite.AuthenticationRequest
import com.example.bloggingwebsite.AuthenticationResponse
import com.example.bloggingwebsite.BaseResponse
import com.example.bloggingwebsite.RegisterRequest

interface UserService {
    fun authentication(request: AuthenticationRequest): AuthenticationResponse
    fun register(request: RegisterRequest): BaseResponse<Any>
}