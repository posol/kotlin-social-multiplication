package ru.posol.microservice.multiplication.domain

import javax.persistence.*

/**
 * Identifies the attempt from a {@link User} to solve a
 * {@link Multiplication}.
 */
@Entity
data class MultiplicationResultAttempt(
        @Id
        @GeneratedValue
        val id: Long = 0,

        @ManyToOne(cascade = arrayOf(CascadeType.PERSIST))
        @JoinColumn(name = "USER_ID")
        val user: User = User(),

        @ManyToOne(cascade = arrayOf(CascadeType.PERSIST))
        @JoinColumn(name = "MULTIPLICATION_ID")
        val multiplication: Multiplication = Multiplication(),

        val resultAttempt: Int = -1,
        val correct: Boolean = false
)