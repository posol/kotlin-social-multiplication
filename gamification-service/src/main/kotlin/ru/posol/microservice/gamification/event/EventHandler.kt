package ru.posol.microservice.gamification.event

import org.slf4j.LoggerFactory
import org.springframework.amqp.AmqpRejectAndDontRequeueException
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import ru.posol.microservice.gamification.service.GameService
import ru.posol.microservice.gamification.service.GameServiceImpl


/**
 * This class receives the events and triggers the associated
 * business logic.
 */
@Component
class EventHandler(
        val gameService: GameService
) {

    val log = LoggerFactory.getLogger(EventHandler::class.java)

    @RabbitListener(queues = arrayOf("\${multiplication.queue}"))
    fun handleMultiplicationSolved(event: MultiplicationSolvedEvent) {
        log.info("Multiplication Solved Event received: ${event.multiplicationResultAttemptId}")
        try {
            gameService.newAttemptForUser(event.userId, event.multiplicationResultAttemptId, event.correct)
        } catch (e: Exception) {
            log.error("Error when trying to process MultiplicationSolvedEvent", e)
            // Avoids the event to be re-queued and reprocessed.
            throw AmqpRejectAndDontRequeueException(e)
        }

    }

}