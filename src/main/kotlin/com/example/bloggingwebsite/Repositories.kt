package com.example.bloggingwebsite

import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.support.JpaEntityInformation
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*


@NoRepositoryBean
interface BaseRepository<T : BaseEntity> : JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {
    fun findByIdAndDeletedFalse(id: UUID): T?
    fun trash(id: UUID): T?
    fun trashList(ids: List<UUID>): List<T?>
    fun findAllNotDeleted(): List<T>
    fun findAllNotDeleted(pageable: Pageable): Page<T>
}

class BaseRepositoryImpl<T : BaseEntity>(
    entityInformation: JpaEntityInformation<T, UUID>, entityManager: EntityManager,
) : SimpleJpaRepository<T, UUID>(entityInformation, entityManager), BaseRepository<T> {

    val isNotDeletedSpecification = Specification<T> { root, _, cb -> cb.equal(root.get<Boolean>("deleted"), false) }

    override fun findByIdAndDeletedFalse(id: UUID) = findByIdOrNull(id)?.run { if (deleted) null else this }

    @Transactional
    override fun trash(id: UUID): T? = findByIdOrNull(id)?.run {
        deleted = true
        save(this)
    }

    override fun findAllNotDeleted(): List<T> = findAll(isNotDeletedSpecification)
    override fun findAllNotDeleted(pageable: Pageable): Page<T> = findAll(isNotDeletedSpecification, pageable)
    override fun trashList(ids: List<UUID>): List<T?> = ids.map { trash(it) }
}


interface CommentRepository : BaseRepository<Comment> {
    fun findByParent(comment: Comment): MutableList<Comment>
}


interface UserRepository : BaseRepository<User> {
    fun findByUsername(username: String): Optional<User>
    fun existsByUsername(username: String): Boolean
}

interface PostRepository : BaseRepository<Post> {


}


interface RoleRepository : BaseRepository<Role> {
    fun findByRoleName(roleName: RoleName): Role;
}
