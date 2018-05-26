package ru.posol.microservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class SocialMultiplicationApplication

fun main(args: Array<String>) {
    runApplication<SocialMultiplicationApplication>(*args)
}
