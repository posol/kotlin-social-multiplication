package ru.posol.microservice.multiplication.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.posol.microservice.multiplication.domain.Multiplication
import ru.posol.microservice.multiplication.service.MultiplicationService

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