package com.example.bloggingwebsite

import java.util.UUID


interface UserProjection {
    fun getId(): UUID
    fun getFirstname(): String
    fun getLastname(): String
}