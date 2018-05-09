package ru.posol.microservice.gamification.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * This class links a Badge to a User. Contains also a  timestamp with the moment
 * in which the user got it.
 */
@Entity
data class BadgeCard(

        @Id
        @GeneratedValue
        @Column(name = "BADGE_ID")
        val badgeId: Long = 0,

        val userId: Long = 0,
        val badgeTimestamp: Long = 0,
        val badge: Badge = Badge.NO_BADGE
)