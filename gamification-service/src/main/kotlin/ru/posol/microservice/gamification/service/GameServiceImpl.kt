package ru.posol.microservice.gamification.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.posol.microservice.gamification.domain.Badge
import ru.posol.microservice.gamification.domain.BadgeCard
import ru.posol.microservice.gamification.domain.GameStats
import ru.posol.microservice.gamification.domain.ScoreCard
import ru.posol.microservice.gamification.repository.BadgeCardRepository
import ru.posol.microservice.gamification.repository.ScoreCardRepository


@Service
class GameServiceImpl(
        @Autowired
        val scoreCardRepository: ScoreCardRepository,
        @Autowired
        val badgeCardRepository: BadgeCardRepository
) : GameService {

    val log = LoggerFactory.getLogger(GameServiceImpl::class.java)

    override fun newAttemptForUser(userId: Long, attemptId: Long, correct: Boolean): GameStats {
        // For the first version we'll give points only if it's correct
        if (correct) {
            val scoreCard = ScoreCard(userId = userId, attemptId = attemptId)
            scoreCardRepository.save(scoreCard)
            log.info("User with id $userId scored ${scoreCard.score} points for attempt id $attemptId")
            val badgeCards = processForBadges(userId, attemptId)
            return GameStats(userId, scoreCard.score, badgeCards.map { it.badge })
        }
        return GameStats.emptyStats(userId);
    }

    override fun retrieveStatsForUser(userId: Long): GameStats {
        val score = scoreCardRepository.getTotalScoreForUser(userId)
        val badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)
        return GameStats(userId, score, badgeCards.map { it.badge })

    }

    /**
     * Checks the total score and the different score cards
    obtained
     * to give new badges in case their conditions are met.
     */
    private fun processForBadges(userId: Long, attemptId: Long): List<BadgeCard> {
        val badgeCards: MutableList<BadgeCard> = mutableListOf()
        val totalScore = scoreCardRepository.getTotalScoreForUser(userId)
        log.info("New score for user $userId is $totalScore")

        val scoreCardsFromDb = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId)
        val badgeCardsFromDb = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)

        // Badges depending on score
        var badgeCard: BadgeCard? = null
        badgeCard = checkAndGiveBadgeBasedOnScore(badgeCardsFromDb, Badge.BRONZE_MULTIPLICATOR, totalScore, 100, userId)
        if (badgeCard != null) badgeCards.add(badgeCard)

        badgeCard = checkAndGiveBadgeBasedOnScore(badgeCardsFromDb, Badge.SILVER_MULTIPLICATOR, totalScore, 500, userId)
        if (badgeCard != null) badgeCards.add(badgeCard)

        badgeCard = checkAndGiveBadgeBasedOnScore(badgeCardsFromDb, Badge.GOLD_MULTIPLICATOR, totalScore, 999, userId)
        if (badgeCard != null) badgeCards.add(badgeCard)

        // First won badge
        if (scoreCardsFromDb.size === 1 && !containsBadge(badgeCardsFromDb, Badge.FIRST_WON)) {
            val firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, userId)
            badgeCards.add(firstWonBadge)
        }
        return badgeCards
    }

    /**
     * Convenience method to check the current score against
     * the different thresholds to gain badges.
     * It also assigns badge to user if the conditions are met.
     */
    private fun checkAndGiveBadgeBasedOnScore(badgeCards: List<BadgeCard>, badge: Badge,
                                              score: Int, scoreThreshold: Int, userId: Long): BadgeCard? {
        return if (score >= scoreThreshold && !containsBadge(badgeCards, badge)) {
            giveBadgeToUser(badge, userId)
        } else null
    }

    /**
     * Checks if the passed list of badges includes the one being checked
     */
    private fun containsBadge(badgeCards: List<BadgeCard>, badge: Badge): Boolean {
        return badgeCards.any { it.badge == badge }
    }

    /**
     * Assigns a new badge to the given user
     */
    private fun giveBadgeToUser(badge: Badge, userId: Long): BadgeCard {
        val badgeCard = BadgeCard(userId = userId, badge = badge)
        badgeCardRepository.save(badgeCard)
        log.info("User with id $userId won a new badge: $badge")
        return badgeCard
    }
}