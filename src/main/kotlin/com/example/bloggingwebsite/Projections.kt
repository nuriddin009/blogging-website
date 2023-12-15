package com.example.bloggingwebsite

import java.util.UUID


interface UserProjection {
    fun getId(): UUID
    fun getFirstname(): String
    fun getLastname(): String
}


interface PostProjection {
    fun getId(): UUID
    fun getTitle(): String
    fun getBody(): String
    fun getViews(): Int
    fun getLikes(): Int
    fun getDislikes(): Int
    fun getTags(): List<TagProjection>
}


interface CommentProjection {
    fun getId(): UUID
    fun getBody(): String
    fun getViews(): Int
    fun getDislikes(): Int
    fun getOwner(): UserProjection
}


interface TagProjection {
    fun getId(): UUID
    fun getName(): String
}


