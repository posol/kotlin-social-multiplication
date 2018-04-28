package ru.posol.socialmultiplication.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * This represents a Multiplication (a * b).
 */
@Entity
data class Multiplication(

        val factorA: Int = 0,
        val factorB: Int = 0,

        @Id
        @GeneratedValue
        @Column(name = "MULTIPLICATION_ID")
        val id: Long = -1
)