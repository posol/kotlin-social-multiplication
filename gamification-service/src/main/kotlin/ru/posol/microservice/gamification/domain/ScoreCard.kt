package ru.posol.microservice.gamification.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * This class represents the Score linked to an attempt in the game,
 * with an associated user and the timestamp in which the score is registered.
 */
@Entity
data class ScoreCard(
        @Id
        @GeneratedValue
        @Column(name = "CARD_ID")
        val cardId: Long = 0,

        @Column(name = "USER_ID")
        val userId: Long = 0,

        @Column(name = "ATTEMPT_ID")
        val attemptId: Long = 0,

        @Column(name = "SCORE_TS")
        val scoreTimestamp: Long = 0,

        @Column(name = "SCORE")
        val score: Int = DEFAULT_SCORE

) {
    companion object {
        val DEFAULT_SCORE = 10
    }

}