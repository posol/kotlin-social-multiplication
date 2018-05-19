package ru.posol.microservice.gamification.event

import java.io.Serializable

/**
 * Event that models the fact that a {@link microservices.book.
multiplication.domain.Multiplication}
 * has been solved in the system. Provides some context
information about the multiplication.
 */
data class MultiplicationSolvedEvent(
        val multiplicationResultAttemptId: Long = 0,
        val userId: Long = 0,
        val correct: Boolean = false
) : Serializable