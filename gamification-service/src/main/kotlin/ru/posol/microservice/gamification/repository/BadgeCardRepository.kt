package ru.posol.microservice.gamification.repository

import org.springframework.data.repository.CrudRepository
import ru.posol.microservice.gamification.domain.BadgeCard

/**
 * Handles data operations with BadgeCards
 */
interface BadgeCardRepository : CrudRepository<BadgeCard, Long> {

    /**
     * Retrieves all BadgeCards for a given user.
     * @param userId the id of the user to look for BadgeCards
     * @return the list of BadgeCards, sorted by most recent.
     */
    fun findByUserIdOrderByBadgeTimestampDesc(userId: Long): List<BadgeCard>
}