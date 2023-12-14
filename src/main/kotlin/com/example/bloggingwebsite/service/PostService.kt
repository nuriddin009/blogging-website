package com.example.bloggingwebsite.service

import com.example.bloggingwebsite.PostRepository
import com.example.bloggingwebsite.ReactionType
import com.example.bloggingwebsite.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

interface PostService {
    fun getOnePost(id: UUID)
    fun getPostsByVerifying(verify: Boolean)
    fun deletePost(id: UUID)
    fun reactionToPost(postId: UUID, reactionType: ReactionType)
    fun createPost()
    fun changePostStatus()
}


@Service
class PostServiceImpl(
    val postRepository: PostRepository,

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

    override fun createPost() {
        TODO("Not yet implemented")
    }

    override fun changePostStatus() {
        TODO("Not yet implemented")
    }


}