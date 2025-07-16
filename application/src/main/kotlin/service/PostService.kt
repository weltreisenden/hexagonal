package service

import exception.PostAccessDeniedException
import exception.PostNotFoundException
import inbound.PostInboundPort
import model.Post
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import port.outbound.PostOutboundPort

@Service
@Transactional
class PostService(
    private val postOutboundPort: PostOutboundPort
) : PostInboundPort {

    override fun createPost(title: String, content: String, username: String): Post {
        val post = Post(
            title = title,
            content = content,
            author = username
        )
        return postOutboundPort.save(post)
    }

    @Transactional(readOnly = true)
    override fun getPost(id: Long): Post {
        val post = postOutboundPort.findById(id)
            ?: throw PostNotFoundException("게시글을 찾을 수 없습니다. ID: $id")

        // 조회수 증가
        val updatedPost = post.increaseViewCount()
        return postOutboundPort.save(updatedPost)
    }

    @Transactional(readOnly = true)
    override fun getAllPosts(): List<Post> {
        return postOutboundPort.findAll()
    }

    override fun updatePost(id: Long, title: String, content: String, username: String): Post {
        val existingPost = postOutboundPort.findById(id)
            ?: throw PostNotFoundException("게시글을 찾을 수 없습니다. ID: $id")

        // 작성자 권한 확인
        if (!existingPost.isWrittenBy(username)) {
            throw PostAccessDeniedException("게시글 수정 권한이 없습니다.")
        }

        val updatedPost = existingPost.update(title, content)
        return postOutboundPort.save(updatedPost)
    }

    override fun deletePost(id: Long, username: String) {
        val existingPost = postOutboundPort.findById(id)
            ?: throw PostNotFoundException("게시글을 찾을 수 없습니다. ID: $id")

        // 작성자 권한 확인
        if (!existingPost.isWrittenBy(username)) {
            throw PostAccessDeniedException("게시글 삭제 권한이 없습니다.")
        }

        postOutboundPort.deleteById(id)
    }
}