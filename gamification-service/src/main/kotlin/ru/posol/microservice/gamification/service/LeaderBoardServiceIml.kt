package ru.posol.microservice.gamification.service

import ru.posol.microservice.gamification.domain.LeaderBoardRow

class LeaderBoardServiceIml : LeaderBoardService {

    override fun getCurrentLeaderBoard(): List<LeaderBoardRow> {
        return emptyList()
    }
}