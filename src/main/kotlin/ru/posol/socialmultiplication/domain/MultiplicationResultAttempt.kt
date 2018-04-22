package ru.posol.socialmultiplication.domain

/**
 * Identifies the attempt from a {@link User} to solve a
 * {@link Multiplication}.
 */
data class MultiplicationResultAttempt(val user: User,
                                       val multiplication: Multiplication,
                                       val resultAttempt: Int = -1)