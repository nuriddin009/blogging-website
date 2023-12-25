package com.example.bloggingwebsite

import com.example.bloggingwebsite.service.PostService
import com.example.bloggingwebsite.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/v1/auth")
class AuthController(
    private val userService: UserService,
) {
    @PostMapping("authenticate")
    fun authentication(@RequestBody request: AuthenticationRequest): AuthenticationResponse =
        userService.authentication(request)

    @PostMapping("register")
    fun register(@RequestBody request: RegisterRequest): BaseResponse<Any> = userService.register(request)
}

@Controller
@RequestMapping("api/user")
class UserController(
    private val userService: UserService,
) {

    @GetMapping("findAll")
    fun getUsers() {

    }

}


@Controller
@RequestMapping("api/post")
class PostController(
    private val postService: PostService,
) {

    @GetMapping
    fun getPosts() {

    }

    fun createPost(@RequestBody postRequest: PostRequest){
        postService.createPost(postRequest)
    }

}


