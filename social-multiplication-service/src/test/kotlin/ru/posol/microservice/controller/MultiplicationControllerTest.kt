package ru.posol.microservice.controller

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
import ru.posol.microservice.multiplication.controller.MultiplicationController
import ru.posol.microservice.multiplication.domain.Multiplication
import ru.posol.microservice.multiplication.service.MultiplicationService

@RunWith(SpringRunner::class)
@WebMvcTest(MultiplicationController::class)
class MultiplicationControllerTest {

    @MockBean
    lateinit var multiplicationService: MultiplicationService

    @Autowired
    lateinit var mvc: MockMvc

    // This object will be magically initialized by the initFields method below.
    lateinit var json: JacksonTester<Multiplication>

    @Before
    fun setUp() {
        JacksonTester.initFields(this, ObjectMapper())
    }

    @Test
    fun getRandomMultiplicationTest() {
        // given
        given(multiplicationService.createRandomMultiplication()).willReturn(Multiplication(70, 20))

        // when
        val response = mvc.perform(get("/multiplications/random").
                accept(MediaType.APPLICATION_JSON)).andReturn().response
        // then
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
        assertThat(response.contentAsString).isEqualTo(json.write(Multiplication(70, 20)).json)
    }

}