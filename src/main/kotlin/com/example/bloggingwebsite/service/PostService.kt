package com.example.bloggingwebsite.service

import com.example.bloggingwebsite.ReactionType
import java.util.UUID

interface PostService {

    fun getOnePost(id: UUID)
    fun getPostsByVerifying(verify: Boolean)
    fun deletePost(id: UUID)
    fun reactionToPost(postId: UUID, reactionType: ReactionType)


}