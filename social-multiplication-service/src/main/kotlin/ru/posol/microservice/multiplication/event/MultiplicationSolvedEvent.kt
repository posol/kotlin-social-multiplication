package ru.posol.microservice.multiplication.event

import java.io.Serializable

/**
 * Event that models the fact that a {@link microservices.book.
multiplication.domain.Multiplication}
 * has been solved in the system. Provides some context
information about the multiplication.
 */
data class MultiplicationSolvedEvent(
        val multiplicationResultAttemptId: Long,
        val userId: Long,
        val correct: Boolean
) : Serializable