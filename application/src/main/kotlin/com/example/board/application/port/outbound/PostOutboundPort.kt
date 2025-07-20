package com.example.board.application.port.outbound

import com.example.board.domain.Post

interface PostOutboundPort {
    fun save(post: Post): Post
    fun findById(id: Long): Post?
    fun findAll(): List<Post>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
}