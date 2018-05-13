package ru.posol.microservice.controler

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import ru.posol.microservice.gamification.controller.UserStatsController
import ru.posol.microservice.gamification.domain.Badge
import ru.posol.microservice.gamification.domain.GameStats
import ru.posol.microservice.gamification.service.GameService


@RunWith(SpringRunner::class)
@WebMvcTest(UserStatsController::class)
class UserStatsControllerTest {

    @MockBean
    lateinit var gameService: GameService

    @Autowired
    lateinit var mvc: MockMvc

    // This object will be magically initialized by the initFields method below.
    lateinit var json: JacksonTester<GameStats>

    @Before
    fun setUp() {
        JacksonTester.initFields(this, ObjectMapper())
    }

    @Test
    fun getUserStatsTest() {
        // given
        val gameStats = GameStats(1L, 2000, listOf(Badge.GOLD_MULTIPLICATOR))
        given(gameService.retrieveStatsForUser(1L)).willReturn(gameStats)

        // when
        val response = mvc.perform(get("/stats?userId=1").accept(MediaType.APPLICATION_JSON))
                .andReturn().response

        // then
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
        assertThat(response.contentAsString).isEqualTo(json.write(gameStats).json)
    }

}