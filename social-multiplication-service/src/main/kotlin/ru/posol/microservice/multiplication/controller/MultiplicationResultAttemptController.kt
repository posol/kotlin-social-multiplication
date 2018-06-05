package ru.posol.microservice.multiplication.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.posol.microservice.multiplication.domain.MultiplicationResultAttempt
import ru.posol.microservice.multiplication.service.MultiplicationService


@RestController
@RequestMapping("/results")
class MultiplicationResultAttemptController(
        @Autowired val multiplicationService: MultiplicationService,
        @Value("\${server.port}")val serverPort: Int
) {

    private val log = LoggerFactory.getLogger(MultiplicationResultAttemptController::class.java)

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

    @GetMapping("/{resultId}")
    fun getResultById(@PathVariable("resultId") resultId: Long): ResponseEntity<MultiplicationResultAttempt> {
        log.info("Retrieving result $resultId from server $serverPort")
        return ResponseEntity.ok(multiplicationService.getResultById(resultId))
    }

}