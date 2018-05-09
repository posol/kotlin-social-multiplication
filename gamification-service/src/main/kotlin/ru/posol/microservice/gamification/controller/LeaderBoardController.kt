package ru.posol.microservice.gamification.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.posol.microservice.gamification.domain.LeaderBoardRow
import ru.posol.microservice.gamification.service.LeaderBoardService

/**
 * This class implements a REST API for the Gamification
LeaderBoard service.
 */
@RestController
@RequestMapping("/leaders")
class LeaderBoardController(
        @Autowired
        val leaderBoardService: LeaderBoardService
) {

    @GetMapping
    fun getLeaderBoard(): List<LeaderBoardRow> = leaderBoardService.getCurrentLeaderBoard();

}