package ru.posol.socialmultiplication

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import ru.posol.socialmultiplication.domain.Multiplication

@SpringBootApplication
class SocialMultiplicationApplication

val logger = LoggerFactory.getLogger(SocialMultiplicationApplication::class.java)

fun main(args: Array<String>) {
    val test = Multiplication(1, 2)
    logger.error("test - $test");
    runApplication<SocialMultiplicationApplication>(*args)
}
