package ru.posol.socialmultiplication.service

import ru.posol.socialmultiplication.domain.Multiplication

interface MultiplicationService {

    /**
     * Creates a Multiplication object with two randomlygenerated
    factors
     * between 11 and 99.
     *
     * @return a Multiplication object with random factors
     */
    fun createRandomMultiplication(): Multiplication

}