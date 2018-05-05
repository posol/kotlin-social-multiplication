package ru.posol.microservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import ru.posol.microservice.multiplication.controller.MultiplicationResultAttemptController
import ru.posol.microservice.multiplication.domain.Multiplication
import ru.posol.microservice.multiplication.domain.MultiplicationResultAttempt
import ru.posol.microservice.multiplication.domain.User
import ru.posol.microservice.multiplication.service.MultiplicationService

@RunWith(SpringRunner::class)
@WebMvcTest(MultiplicationResultAttemptController::class)
class MultiplicationResultAttemptControllerTest {

    @MockBean
    lateinit var multiplicationService: MultiplicationService

    @Autowired
    lateinit var mvc: MockMvc

    // This object will be magically initialized by the initFields method below.
    lateinit var jsonResult: JacksonTester<MultiplicationResultAttempt>
    lateinit var jsonResponse: JacksonTester<MultiplicationResultAttempt>
    lateinit var jsonResultAttempt: JacksonTester<MultiplicationResultAttempt>
    lateinit var jsonResultAttemptList: JacksonTester<List<MultiplicationResultAttempt>>

    @Before
    fun setUp() {
        JacksonTester.initFields(this, ObjectMapper())
    }

    @Test
    fun getUserStats() {
        // given
        val user = User(alias = "posol")
        val multiplication = Multiplication(50, 70)
        val attempt = MultiplicationResultAttempt(user = user, multiplication = multiplication, resultAttempt = 3500)
        val recentAttempts = listOf(attempt, attempt)
        given(multiplicationService.getStatsForUser("posol")).willReturn(recentAttempts)

        // when
        val response = mvc.perform(get("/results").param("alias", "posol")).andReturn().response

        // then
        Assertions.assertThat(response.status).isEqualTo(HttpStatus.OK.value())
        Assertions.assertThat(response.contentAsString).isEqualTo(jsonResultAttemptList.write(recentAttempts).json)
    }

    @Test
    fun postResultReturnCorrect() {
        genericParameterizedTest(true)
    }

    @Test
    fun postResultReturnNotCorrect() {
        genericParameterizedTest(false)
    }

    fun genericParameterizedTest(correct: Boolean) {
        // given
        val user = User(alias = "posol")
        val multiplication = Multiplication(50, 70)
        val attempt = MultiplicationResultAttempt(user = user, multiplication = multiplication, resultAttempt = 3500, correct = correct)
        val copyAttempt = attempt.copy()

        // checkAttempt function is only taking a Non-Null parameter! However, in Mockito, any() will
        // return Null in it’s verification function when being used. if any isNull - take attempt obj
        given(multiplicationService.checkAttempt(any(MultiplicationResultAttempt::class.java)
                ?: attempt)).willReturn(correct)

        // when
        val response = mvc.perform(post("/results").contentType(MediaType.APPLICATION_JSON).
                content(jsonResult.write(attempt).json)).andReturn().response

        // then
        Assertions.assertThat(response.status).isEqualTo(HttpStatus.OK.value())
        Assertions.assertThat(response.contentAsString).isEqualTo(jsonResponse.write(copyAttempt).json)
    }

}