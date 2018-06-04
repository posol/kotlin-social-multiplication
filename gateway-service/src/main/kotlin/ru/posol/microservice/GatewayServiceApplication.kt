package ru.posol.microservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
class GatewayServiceApplication

fun main(args: Array<String>) {
    runApplication<GatewayServiceApplication>(*args)
}
