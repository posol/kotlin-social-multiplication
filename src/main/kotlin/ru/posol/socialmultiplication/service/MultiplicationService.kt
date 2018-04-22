package ru.posol.socialmultiplication.service

import ru.posol.socialmultiplication.domain.Multiplication
import ru.posol.socialmultiplication.domain.MultiplicationResultAttempt


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


}