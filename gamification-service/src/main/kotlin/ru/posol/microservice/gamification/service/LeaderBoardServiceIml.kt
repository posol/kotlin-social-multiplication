package ru.posol.microservice.gamification.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.posol.microservice.gamification.domain.LeaderBoardRow
import ru.posol.microservice.gamification.repository.ScoreCardRepository

@Service
class LeaderBoardServiceIml(
        @Autowired
        val scoreCardRepository: ScoreCardRepository
) : LeaderBoardService {

    override fun getCurrentLeaderBoard(): List<LeaderBoardRow> {
        return scoreCardRepository.findFirst10()
    }
}