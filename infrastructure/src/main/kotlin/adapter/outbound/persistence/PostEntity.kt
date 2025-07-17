package adapter.outbound.persistence

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
class PostEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "title")
    var title: String = "",

    @Column(name = "content")
    var content: String = "",

    @Column(name = "author")
    var author: String = "",

    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var viewCount: Int = 0
) {
    protected constructor() : this(null, "", "", "", LocalDateTime.now(), LocalDateTime.now(), 0)

    constructor(
        title : String,
        content: String,
        author: String,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime,
        viewCount: Int = 0
    ) : this(null, title, content, author, createdAt, updatedAt, viewCount)
}