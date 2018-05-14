package ru.posol.microservice.gamification.client.dto

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import ru.posol.microservice.gamification.client.MultiplicationResultAttemptDeserializer

/**
 * Identifies the attempt from a user to solve a multiplication.
 */
@JsonDeserialize(using = MultiplicationResultAttemptDeserializer::class)
data class MultiplicationResultAttempt(
        val userAlias: String = "guest",
        val multiplicationFactorA: Int = -1,
        val multiplicationFactorB: Int = -1,
        val resultAttempt: Int = -1,
        val correct: Boolean = false
)