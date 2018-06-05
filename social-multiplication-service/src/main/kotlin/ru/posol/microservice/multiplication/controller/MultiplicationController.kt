package ru.posol.microservice.multiplication.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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
class MultiplicationController(
        @Autowired val multiplicationService: MultiplicationService,
        @Value("\${server.port}") val serverPort: Int
) {

    private val log = LoggerFactory.getLogger(MultiplicationController::class.java)

    @GetMapping("/random")
    fun getRandomMultiplication(): Multiplication {
        log.info("Generating a random multiplication from server $serverPort")
        return multiplicationService.createRandomMultiplication()
    }

}