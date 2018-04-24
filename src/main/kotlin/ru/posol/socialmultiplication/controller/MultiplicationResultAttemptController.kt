package ru.posol.socialmultiplication.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.posol.socialmultiplication.domain.MultiplicationResultAttempt
import ru.posol.socialmultiplication.service.MultiplicationService

@RestController
@RequestMapping("/results")
class MultiplicationResultAttemptController(@Autowired val multiplicationService: MultiplicationService) {

    @PostMapping
    fun postResult(@RequestBody multiplicationResultAttempt: MultiplicationResultAttempt) =
            ResponseEntity.ok(ResultResponse(multiplicationService.checkAttempt(multiplicationResultAttempt)))

}

data class ResultResponse(val correct: Boolean)