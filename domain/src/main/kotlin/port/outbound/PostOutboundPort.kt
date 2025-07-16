package port.outbound

import model.Post

interface PostOutboundPort {
    fun save(post: Post): Post
    fun findById(id: Long): Post?
    fun findAll(): List<Post>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
}