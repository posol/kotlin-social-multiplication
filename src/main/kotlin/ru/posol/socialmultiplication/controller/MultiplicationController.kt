package ru.posol.socialmultiplication.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.posol.socialmultiplication.domain.Multiplication
import ru.posol.socialmultiplication.service.MultiplicationService

/**
 * This class implements a REST API for our Multiplication
application.
 */
@RestController
@RequestMapping("/multiplications")
class MultiplicationController(@Autowired val multiplicationService: MultiplicationService) {

    @GetMapping("/random")
    fun getRandomMultiplication(): Multiplication = multiplicationService.createRandomMultiplication()

}