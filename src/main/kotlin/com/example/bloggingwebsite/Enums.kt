package com.example.bloggingwebsite

enum class RoleName {
    ROLE_USER, ROLE_ADMIN
}


enum class ErrorCode(val code: Int) {
    USERNAME_EXISTS(100),
    USER_NOT_FOUND(101),
}

enum class ReactionType {
    LIKE,
    DISLIKE
}

enum class Status {
    ACTIVE, BLOCK, CONFIRM;
}

