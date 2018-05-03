package ru.posol.socialmultiplication.event

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Handles the communication with the Event Bus.
 */
@Component
class EventDispatcher(
        @Autowired
        val rabbitTemplate: RabbitTemplate,
        // The exchange to use to send anything related to Multiplication
        @Value("\${multiplication.exchange}")
        val multiplicationExchange: String,
        // The routing key to use to send this particular event
        @Value("\${{multiplication.solved.key}")
        val  multiplicationSolvedRoutingKey: String
) {

    fun send(multiplicationSolvedEvent: MultiplicationSolvedEvent) {
        rabbitTemplate.convertAndSend(multiplicationExchange,
                                      multiplicationSolvedRoutingKey,
                                      multiplicationSolvedEvent)
    }

}