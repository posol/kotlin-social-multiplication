package ru.posol.microservice.gamification.domain

/**
 * Represents a line in our Leaderboard: it links a user to a total score.
 */
data class LeaderBoardRow(
    val userId: Long = 0,
    val totalScore: Long = 0
)