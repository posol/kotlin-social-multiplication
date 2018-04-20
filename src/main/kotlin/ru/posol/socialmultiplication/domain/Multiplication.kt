package ru.posol.socialmultiplication.domain

/**
 * This class represents a Multiplication in our application.
 */
class Multiplication(val factorA: Int, val factorB: Int) {

    // The result of the operation A * B
    val result: Int

    init {
        result = factorA * factorB
    }


    override fun toString(): String {
        return "Multiplication(factorA=$factorA, factorB=$factorB, result=$result)"
    }


}