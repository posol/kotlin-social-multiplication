package ru.posol.microservice.event

import org.junit.Before
import org.junit.Test
import ru.posol.microservice.gamification.service.GameService
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.posol.microservice.gamification.event.EventHandler
import ru.posol.microservice.gamification.service.GameServiceImpl
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mockito.*
import ru.posol.microservice.gamification.event.MultiplicationSolvedEvent
import ru.posol.microservice.gamification.domain.GameStats

class EventHandlerTest {

    @Mock
    lateinit var gameService: GameService

    lateinit var eventHandler: EventHandler

    @Before
    fun setUp() {
        // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this)
        eventHandler = EventHandler(gameService)
  }

    @Test
    fun multiplicationAttemptReceivedTest() {
        // given
        val userId = 1L
        val attemptId = 8L
        val correct = true
        val gameStatsExpected = GameStats()
        val event = MultiplicationSolvedEvent(attemptId, userId, correct)
        given(gameService.newAttemptForUser(userId, attemptId, correct)).willReturn(gameStatsExpected)

        // when
        eventHandler.handleMultiplicationSolved(event)

        // then
        verify(gameService).newAttemptForUser(userId, attemptId, correct)
    }


}

