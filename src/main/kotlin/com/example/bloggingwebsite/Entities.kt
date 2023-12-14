package com.example.bloggingwebsite

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.*


@MappedSuperclass
class BaseEntity(
    @Id @GeneratedValue(strategy = GenerationType.UUID) var id: UUID? = null,
    @CreatedBy @ManyToOne var createdBy: User? = null,
    @LastModifiedBy @JoinColumn(updatable = false) @ManyToOne var updatedBy: User? = null,
    @CreationTimestamp @Temporal(TemporalType.TIMESTAMP) var createdDate: LocalDateTime? = null,
    @UpdateTimestamp @Temporal(TemporalType.TIMESTAMP) var updatedDate: LocalDateTime? = null,
    @Column(nullable = false) @ColumnDefault(value = "false") var deleted: Boolean = false,
)

@Table(name = "users")
@Entity
class User(
    var firstname: String,
    var lastname: String,
    var username: String,
    var password: String,
    @Enumerated(EnumType.STRING) var status: Status,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: Set<Role> = HashSet(),
) : BaseEntity()


@Entity
class Role(
    @Enumerated(EnumType.STRING) var roleName: RoleName,
    @ManyToMany(mappedBy = "roles")
    val users: Set<User> = HashSet(),
) : BaseEntity()


@Entity
class Post(
    @NotBlank(message = "Title can't be empty.")
    @Size(min = 3, message = "A title must be at least 3 characters.")
    @Column(nullable = false)
    var title: String,
    @NotBlank(message = "Body can't be empty")
    @Column(nullable = false, columnDefinition = "text")
    var body: String,
    var views: Int,
    @ColumnDefault("false")
    var verified: Boolean,
    var likes: Int,
    var dislikes: Int,
    @ManyToMany
    @JoinTable(
        name = "post_tags",
        joinColumns = [JoinColumn(name = "post_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    val tags: MutableList<Tag>,
) : BaseEntity()


@Entity
class Comment(
    @NotBlank(message = "Body can't be empty")
    @Column(nullable = false, length = 3000)
    var body: String,
    var likes: Int,
    var dislikes: Int,
    @ColumnDefault("false")
    var verified: Boolean,
    @ManyToOne @JsonManagedReference var user: User,
    @ManyToOne @JsonManagedReference var post: Post,
    @ManyToOne var parent: Comment?,
) : BaseEntity()


@Entity
@Table(name = "tags")
class Tag(
    @Column(nullable = false)
    var name: String,
    @ColumnDefault("false")
    var verified: Boolean,
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    var posts: MutableList<Post>,
) : BaseEntity()

@Entity
class PostReaction(
    @Enumerated(EnumType.STRING)
    var reaction: ReactionType,
    @ManyToOne var post: Post,
    @ManyToOne(cascade = [CascadeType.PERSIST]) var user: User,
) : BaseEntity()


@Entity
class CommentReaction(
    @Enumerated(EnumType.STRING)
    var reaction: ReactionType,
    @ManyToOne var post: Post,
    @ManyToOne(cascade = [CascadeType.PERSIST]) var user: User,
) : BaseEntity()







