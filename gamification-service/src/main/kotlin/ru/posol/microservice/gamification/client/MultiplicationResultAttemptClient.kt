package ru.posol.microservice.gamification.client

import ru.posol.microservice.gamification.client.dto.MultiplicationResultAttempt


/**
 * This interface allows us to connect to the Multiplication microservice.
 * Note that it's agnostic to the way of communication.
 */
interface MultiplicationResultAttemptClient {

    fun retrieveMultiplicationResultAttemptbyId(multiplicationId: Long): MultiplicationResultAttempt?

}