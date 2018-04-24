package ru.posol.socialmultiplication.domain

/**
 * This represents a Multiplication (a * b).
 */
class Multiplication(val factorA: Int = 0, val factorB: Int = 0) {

    // The result of the operation A * B
    val result: Int

    init {
        result = factorA * factorB
    }

    override fun toString() = "Multiplication(factorA=$factorA, factorB=$factorB, result=$result)"

}