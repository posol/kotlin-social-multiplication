package ru.posol.microservice.multiplication.service

import ru.posol.microservice.multiplication.domain.Multiplication
import ru.posol.microservice.multiplication.domain.MultiplicationResultAttempt


interface MultiplicationService {

    /**
     * Generates a random {@link Multiplication} object.
     *
     * @return a multiplication of randomly generated numbers
     */
    fun createRandomMultiplication(): Multiplication

    /**
     * @return true if the attempt matches the result of the
     * multiplication, false otherwise.
     */
    fun checkAttempt(resultAttempt: MultiplicationResultAttempt): Boolean

    fun getStatsForUser(userAlias: String): List<MultiplicationResultAttempt>

    fun getResultById(resultId: Long): MultiplicationResultAttempt
}