package com.example.board.application.port.inbound

import com.example.board.domain.Post

interface PostInboundPort {
    fun createPost(title: String, content: String, username: String): Post
    fun getPost(id: Long): Post
    fun getAllPosts(): List<Post>
    fun updatePost(id: Long, title: String, content: String, username: String): Post
    fun deletePost(id: Long, username: String)
}