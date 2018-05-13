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
import ru.posol.microservice.gamification.controller.LeaderBoardController
import ru.posol.microservice.gamification.domain.LeaderBoardRow
import ru.posol.microservice.gamification.service.LeaderBoardService


@RunWith(SpringRunner::class)
@WebMvcTest(LeaderBoardController::class)
class LeaderBoardControllerTest {

    @MockBean
    lateinit var leaderBoardService: LeaderBoardService

    @Autowired
    lateinit var mvc: MockMvc

    // This object will be magically initialized by the initFields method below.
    lateinit var json: JacksonTester<List<LeaderBoardRow>>

    @Before
    fun setUp() {
        JacksonTester.initFields(this, ObjectMapper())
    }

    @Test
    fun getRandomMultiplicationTest() {
        // given
        val leaderBoardRow1 = LeaderBoardRow(1L, 500L)
        val leaderBoardRow2 = LeaderBoardRow(2L, 400L)
        val leaderBoard = listOf(leaderBoardRow1,leaderBoardRow2)
        given(leaderBoardService.getCurrentLeaderBoard()).willReturn(leaderBoard)

        // when
        val response = mvc.perform(get("/leaders").accept(MediaType.APPLICATION_JSON)).andReturn().response

        // then
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
        assertThat(response.contentAsString).isEqualTo(json.write(leaderBoard).json)
    }

}