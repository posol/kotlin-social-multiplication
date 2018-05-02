package ru.posol.socialmultiplication.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * Stores information to identify the user.
 */
@Entity
data class User(
        @Id
        @GeneratedValue
        @Column(name = "USER_ID")
        val id: Long = 0,

        val alias: String = "guest"
)