package inbound

import model.Post

interface PostInboundPort {
    fun createPost(title: String, content: String, username: String): Post
    fun getPost(id: Long): Post
    fun getAllPosts(): List<Post>
    fun updatePost(id: Long, title: String, content: String, username: String): Post
    fun deletePost(id: Long, username: String)
}