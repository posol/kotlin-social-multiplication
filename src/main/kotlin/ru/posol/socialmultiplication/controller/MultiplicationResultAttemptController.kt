package ru.posol.socialmultiplication.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.posol.socialmultiplication.domain.MultiplicationResultAttempt
import ru.posol.socialmultiplication.service.MultiplicationService

@RestController
@RequestMapping("/results")
class MultiplicationResultAttemptController(@Autowired val multiplicationService: MultiplicationService) {

    @PostMapping
    fun postResult(@RequestBody multiplicationResultAttempt: MultiplicationResultAttempt): ResponseEntity<MultiplicationResultAttempt> {
        val isCorrect = multiplicationService.checkAttempt(multiplicationResultAttempt)
        val checkedAttempt = multiplicationResultAttempt.copy(correct = isCorrect)
        return ResponseEntity.ok(checkedAttempt)
    }

    @GetMapping
    fun getStatistics(@RequestParam("alias") alias: String): ResponseEntity<List<MultiplicationResultAttempt>> {
        return ResponseEntity.ok(multiplicationService.getStatsForUser(alias))
    }


}