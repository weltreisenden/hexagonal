package com.example.board.domain

import java.time.LocalDateTime

data class Post(
    val id: Long? = null,
    val title: String,
    val content: String,
    val author: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val viewCount: Int = 0
) {

    fun increaseViewCount(): Post {
        return this.copy(
            viewCount = this.viewCount + 1)
    }

    fun update(title: String, content: String): Post {
        return this.copy(
            title = title,
            content = content,
            updatedAt = LocalDateTime.now()
        )
    }

    fun isWrittenBy(userName: String): Boolean {
        return this.author == userName
    }

}