package ru.posol.microservice.gamification.domain

/**
 * This object contains the result of one or many iterations of the game.
 * It may contain any combination of {@link ScoreCard} objects and {@link BadgeCard} objects.
 * It can be used as a delta (as a single game iteration) or to represent the total amount of score / badges.
 */
data class GameStats(
        val userId: Long = 0,
        val score: Int = 0,
        val badges: List<Badge> = emptyList()
) {
    /**
     * Factory method to build an empty instance (zero points and no badges)
     * @param userId the user's id
     * @return a {@link GameStats} object with zero score and no badges
     */
    companion object {
        fun emptyStats(userId: Long): GameStats = GameStats(userId = userId)
    }

}