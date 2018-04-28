package ru.posol.socialmultiplication.repository

import org.springframework.data.repository.CrudRepository
import ru.posol.socialmultiplication.domain.User

/**
 * This interface allows us to save and retrieve Users
 */
interface UserRepository : CrudRepository<User, Long> {

    fun findByAlias(alias: String): User?

}