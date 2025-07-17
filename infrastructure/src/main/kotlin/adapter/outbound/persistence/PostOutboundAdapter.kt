package adapter.outbound.persistence

import model.Post
import port.outbound.PostOutboundPort
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
        return savedEntity.toDomain()
    }

    override fun findById(id: Long): Post? {
        return postJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findAll(): List<Post> {
        return postJpaRepository.findAll()
            .map { it.toDomain() }
    }

    override fun deleteById(id: Long) {
        postJpaRepository.deleteById(id)
    }

    override fun existsById(id: Long): Boolean {
        return postJpaRepository.existsById(id)
    }
}

fun PostEntity.toDomain(): Post {
    return Post(
        id = this.id,
        title = this.title,
        content = this.content,
        author = this.author,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        viewCount = this.viewCount
    )
}
