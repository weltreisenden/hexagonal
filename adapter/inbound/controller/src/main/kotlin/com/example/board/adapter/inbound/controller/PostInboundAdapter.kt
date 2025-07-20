package com.example.board.adapter.inbound.controller

import com.example.board.application.port.inbound.PostInboundPort
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import com.example.board.domain.Post
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/posts")
class PostInboundAdapter (
    private val postInboundPort: PostInboundPort
) {

    @PostMapping
    fun createPost(@Valid @RequestBody request: CreatePostRequest): ResponseEntity<PostResponseDto> {
        val authentication = SecurityContextHolder.getContext().authentication
        val username = authentication?.name ?: throw RuntimeException("인증이 필요합니다")

        val post = postInboundPort.createPost(
            title = request.title,
            content = request.content,
            username = username
        )

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(post.toResponseDto())
    }

    @GetMapping
    fun getAllPosts(): ResponseEntity<List<PostListResponseDto>> {
        val posts = postInboundPort.getAllPosts()
        val response = posts.map { it.toListResponseDto() }
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: Long): ResponseEntity<PostResponseDto> {
        val post = postInboundPort.getPost(id)
        return ResponseEntity.ok(post.toResponseDto())
    }

    @PutMapping("/{id}")
    fun updatePost(@PathVariable id: Long, @Valid @RequestBody request: UpdatePostRequest) : ResponseEntity<PostResponseDto> {
        val authentication = SecurityContextHolder.getContext().authentication
        val username = authentication?.name ?: throw RuntimeException("인증이 필요합니다")

        val post = postInboundPort.updatePost(id, request.title, request.content, username)
        return ResponseEntity.ok(post.toResponseDto())
    }

    fun deletePost(@PathVariable id: Long): ResponseEntity<Void> {
        val authentication = SecurityContextHolder.getContext().authentication
        val username = authentication?.name ?: throw RuntimeException("인증이 필요합니다")

        postInboundPort.deletePost(id, username)
        return ResponseEntity.noContent().build()
    }

}

data class CreatePostRequest(

    @field:NotBlank(message = "제목은 필수입니다")
    @field:Size(max = 100, message = "제목은 100자 이하여야 합니다")
    val title: String,

    @field:NotBlank(message = "내용은 필수입니다")
    val content: String
)

data class UpdatePostRequest(

    @field:NotBlank(message = "제목은 필수입니다")
    @field:Size(max = 100, message = "제목은 100자 이하여야 합니다")
    val title: String,

    @field:NotBlank(message = "내용은 필수입니다")
    val content: String
)

data class PostResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val author: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val viewCount: Int
)

data class PostListResponseDto(
    val id: Long,
    val title: String,
    val author: String,
    val createdAt: LocalDateTime,
    val viewCount: Int
)

fun Post.toResponseDto(): PostResponseDto {
    return PostResponseDto(
        id = this.id!!,
        title = this.title,
        content = this.content,
        author = this.author,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        viewCount = this.viewCount
    )
}

fun Post.toListResponseDto(): PostListResponseDto {
    return PostListResponseDto(
        id = this.id!!,
        title = this.title,
        author = this.author,
        createdAt = this.createdAt,
        viewCount = this.viewCount
    )
}