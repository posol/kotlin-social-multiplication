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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Multiplication

        if (factorA != other.factorA) return false
        if (factorB != other.factorB) return false
        if (result != other.result) return false

        return true
    }

    override fun hashCode(): Int {
        var result1 = factorA
        result1 = 31 * result1 + factorB
        result1 = 31 * result1 + result
        return result1
    }

}