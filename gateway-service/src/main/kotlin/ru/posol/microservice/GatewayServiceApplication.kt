package ru.posol.microservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients
import ru.posol.microservice.gateway.configuration.RibbonConfiguration

@EnableZuulProxy
@EnableEurekaClient
@RibbonClients(defaultConfiguration = [RibbonConfiguration::class])
@SpringBootApplication
class GatewayServiceApplication

fun main(args: Array<String>) {
    runApplication<GatewayServiceApplication>(*args)
}
