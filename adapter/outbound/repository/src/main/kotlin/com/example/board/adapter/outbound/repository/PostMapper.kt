package com.example.board.adapter.outbound.repository

import com.example.board.domain.Post
import com.example.board.infrastructure.jpa.PostEntity

object PostMapper {
    fun mapEntityToDomain(entity: PostEntity): Post {
        return with(entity) {
            Post(
                id = this.id,
                title = this.title,
                content = this.content,
                author = this.author,
                createdAt = this.createdAt,
                updatedAt = this.updatedAt,
                viewCount = this.viewCount
            )
        }
    }
}