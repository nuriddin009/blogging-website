package com.example.bloggingwebsite.service.impl

import com.example.bloggingwebsite.PostRepository
import com.example.bloggingwebsite.ReactionType
import com.example.bloggingwebsite.service.PostService
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostServiceImpl(
    val postRepository: PostRepository
) : PostService {
    override fun getOnePost(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun getPostsByVerifying(verify: Boolean) {
        TODO("Not yet implemented")
    }

    override fun deletePost(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun reactionToPost(postId: UUID, reactionType: ReactionType) {
        TODO("Not yet implemented")
    }


}