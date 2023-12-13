package com.example.bloggingwebsite

import com.example.bloggingwebsite.service.PostService
import com.example.bloggingwebsite.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("api/user")
class UserController(
    private val userService: UserService,
) {

    @GetMapping
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

}


