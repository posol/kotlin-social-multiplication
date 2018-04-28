package ru.posol.socialmultiplication.repository

import org.springframework.data.repository.CrudRepository
import ru.posol.socialmultiplication.domain.MultiplicationResultAttempt

/**
 * This interface allow us to store and retrieve attempts
 */
interface MultiplicationResultAttemptRepository : CrudRepository<MultiplicationResultAttempt, Long> {

    /**
     * @return the latest 5 attempts for a given user,
    identified by their alias.
     */
    fun findTop5ByUserAliasOrderByIdDesc(userAlias: String): List<MultiplicationResultAttempt>

}