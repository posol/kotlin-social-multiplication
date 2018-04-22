package ru.posol.socialmultiplication.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import ru.posol.socialmultiplication.service.MultiplicationService

/**
 * This class implements a REST API for our Multiplication
application.
 */
@RestController
class MultiplicationController(@Autowired val multiplicationService: MultiplicationService) {
}