package com.example.bloggingwebsite.service

import com.example.bloggingwebsite.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.UUID

interface PostService {
    fun getOnePost(id: UUID): Post
    fun getPostsByVerifying(verify: Boolean, page: Int, size: Int): Page<PostProjection>
    fun deletePost(id: UUID)
    fun reactionToPost(postId: UUID, reactionType: ReactionType)
    fun createPost(postRequest: PostRequest): ApiResponse<Any>
    fun changePostStatus()
}


@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userSession: UserSession,
    private val postReactionRepository: PostReactionRepository,
    private val tagRepository: TagRepository,
) : PostService {


    override fun getOnePost(id: UUID): Post {
        return postRepository.findByIdAndDeletedFalse(id)!!
    }

    override fun getPostsByVerifying(verify: Boolean, page: Int, size: Int): Page<PostProjection> {
        return postRepository.findAllByVerifiedAndDeletedFalse(verify, PageRequest.of(page - 1, size))
    }

    override fun deletePost(id: UUID) {
        postRepository.trash(id)
    }

    override fun reactionToPost(postId: UUID, reactionType: ReactionType) {
        val post = postRepository.findByIdAndDeletedFalse(postId)

        val postReactionOptional =
            postReactionRepository.findByUserAndPostAndDeletedFalse(userSession.getUser()!!, post!!)

        if (postReactionOptional.isPresent) {
            val postReaction = postReactionOptional.get()

            if (postReaction.reaction == reactionType) {
                postReactionRepository.trash(postReaction.id!!)
                if (reactionType == ReactionType.LIKE) {
                    post.likes--
                } else {
                    post.dislikes--
                }
            } else {
                postReaction.reaction = reactionType
                if (reactionType == ReactionType.LIKE) {
                    post.likes++
                    post.dislikes--
                } else {
                    post.likes--
                    post.dislikes++
                }
            }
        } else {
            postReactionRepository.save(PostReaction(reactionType, post, userSession.getUser()!!))
            if (reactionType == ReactionType.LIKE) {
                post.likes++
            } else {
                post.dislikes++
            }

        }
        postRepository.save(post)
    }

    override fun createPost(postRequest: PostRequest): ApiResponse<Any> {
        val tags = postRequest.tagIds.map { tagRepository.findByIdAndDeletedFalse(it)!! }
        val post = postRepository.save(
            Post(
                postRequest.title,
                postRequest.body,
                0,
                false,
                0,
                0,
                userSession.getUser()!!,
                tags
            )
        )
        return ApiResponse(post, true, "post created")
    }

    override fun changePostStatus() {
        TODO("Not yet implemented")
    }


}