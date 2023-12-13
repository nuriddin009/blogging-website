package com.example.bloggingwebsite.service.impl

import com.example.bloggingwebsite.PostRepository
import com.example.bloggingwebsite.service.PostService
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostServiceImpl(
    val postRepository: PostRepository
) : PostService {
    override fun getOnePost(id: UUID) {
        val post = postRepository.findByIdAndDeletedFalse(id)
    }

    override fun getPostsByVerifying() {
        TODO("Not yet implemented")
    }

    override fun deletePost() {
        TODO("Not yet implemented")
    }

    override fun reactionToPost() {
        TODO("Not yet implemented")
    }


}