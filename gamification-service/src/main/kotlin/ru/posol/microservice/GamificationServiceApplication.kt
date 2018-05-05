package ru.posol.microservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GamificationServiceApplication

fun main(args: Array<String>) {
    runApplication<GamificationServiceApplication>(*args)
}
