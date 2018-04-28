package ru.posol.socialmultiplication.repository

import org.springframework.data.repository.CrudRepository
import ru.posol.socialmultiplication.domain.Multiplication

/**
 * This interface allows us to save and retrieve
Multiplications
 */
interface MultiplicationRepository : CrudRepository<Multiplication, Long> {

    fun findByFactorAAndFactorB(factorA: Int, factorB: Int): Multiplication?

}