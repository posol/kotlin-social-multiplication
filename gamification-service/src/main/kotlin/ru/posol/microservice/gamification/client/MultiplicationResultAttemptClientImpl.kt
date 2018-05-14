package ru.posol.microservice.gamification.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import ru.posol.microservice.gamification.client.dto.MultiplicationResultAttempt


/**
 * This implementation of MultiplicationResultAttemptClient interface connects to
 * the Multiplication microservice via REST.
 */
@Component
class MultiplicationResultAttemptClientImpl(
        @Autowired
        val restTemplate: RestTemplate,
        @Value("\${multiplicationHost}")
        val multiplicationHost: String
) : MultiplicationResultAttemptClient {

    override fun retrieveMultiplicationResultAttemptbyId(multiplicationId: Long): MultiplicationResultAttempt? {
        return restTemplate.getForObject(multiplicationHost + "/results/" + multiplicationId, MultiplicationResultAttempt::class.java)
    }
}