package ru.posol.microservice.gamification.service

import ru.posol.microservice.gamification.domain.LeaderBoardRow

/**
 * Provides methods to access the LeaderBoard with users and scores.
 */
interface LeaderBoardService {

    /**
     * Retrieves the current leader board with the top score users
     * @return the users with the highest score
     */
    fun getCurrentLeaderBoard(): List<LeaderBoardRow>
}