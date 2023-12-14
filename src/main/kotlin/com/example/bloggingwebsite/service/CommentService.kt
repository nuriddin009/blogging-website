package com.example.bloggingwebsite.service

import com.example.bloggingwebsite.Comment
import com.example.bloggingwebsite.CommentRepository
import org.springframework.stereotype.Service
import java.util.UUID

interface CommentService {
    fun getPostComments(postId: UUID): List<Comment>
}

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
) : CommentService {
    override fun getPostComments(postId: UUID): List<Comment> {
        TODO("Not yet implemented")
    }

}