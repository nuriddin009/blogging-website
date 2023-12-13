package com.example.bloggingwebsite.service

import java.util.UUID

interface PostService {

    fun getOnePost(id: UUID)
    fun getPostsByVerifying()
    fun deletePost()
    fun reactionToPost()


}