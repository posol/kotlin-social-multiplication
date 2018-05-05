package ru.posol.microservice.gamification.domain

/**
 * Enumeration with the different types of Badges that a user can win.
 */
enum class Badge {

    // Badges depending on score
    BRONZE_MULTIPLICATOR,
    SILVER_MULTIPLICATOR,
    GOLD_MULTIPLICATOR,

    // Other badges won for different conditions
    FIRST_ATTEMPT,
    FIRST_WON

}