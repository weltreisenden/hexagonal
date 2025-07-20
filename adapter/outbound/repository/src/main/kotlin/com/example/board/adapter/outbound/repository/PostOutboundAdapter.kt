package com.example.board.adapter.outbound.repository

import com.example.board.domain.Post
import com.example.board.application.port.outbound.PostOutboundPort
import com.example.board.infrastructure.jpa.PostEntity
import com.example.board.infrastructure.jpa.PostJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class PostOutboundAdapter(
    private val postJpaRepository: PostJpaRepository
) : PostOutboundPort {

    override fun save(post: Post): Post {
        val entity = if (post.id == null) {
            PostEntity(
                title = post.title,
                content = post.content,
                author = post.author,
                createdAt = post.createdAt,
                updatedAt = post.updatedAt,
                viewCount = post.viewCount
            )
        } else {
            val existingEntity = postJpaRepository.findById(post.id!!)
                .orElseThrow { IllegalArgumentException("Post not found with id: ${post.id}") }

            existingEntity.apply {
                title = post.title
                content = post.content
                author = post.author
                createdAt = post.createdAt
                updatedAt = post.updatedAt
                viewCount = post.viewCount
            }
            existingEntity
        }

        val savedEntity = postJpaRepository.save(entity)
        return PostMapper.mapEntityToDomain(savedEntity)
    }

    override fun findById(id: Long): Post? {
        return postJpaRepository.findByIdOrNull(id)
            ?.let { PostMapper.mapEntityToDomain(it) }
    }

    override fun findAll(): List<Post> {
        return postJpaRepository.findAll().map { PostMapper.mapEntityToDomain(it) }
    }

    override fun deleteById(id: Long) {
        postJpaRepository.deleteById(id)
    }

    override fun existsById(id: Long): Boolean {
        return postJpaRepository.existsById(id)
    }
}
