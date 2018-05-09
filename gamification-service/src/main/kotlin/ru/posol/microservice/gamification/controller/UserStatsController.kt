package ru.posol.microservice.gamification.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.posol.microservice.gamification.domain.GameStats
import ru.posol.microservice.gamification.service.GameService


/**
 * This class implements a REST API for the Gamification User
Statistics service.
 */
@RestController
@RequestMapping("/stats")
class UserStatsController(
        val gameService: GameService
) {

    @GetMapping
    fun getStatsForUser(@RequestParam("userId") userId: Long): GameStats {
        return gameService.retrieveStatsForUser(userId)
    }

}