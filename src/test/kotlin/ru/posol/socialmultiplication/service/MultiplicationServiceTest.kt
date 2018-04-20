package ru.posol.socialmultiplication.service

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.assertj.core.api.Assertions.assertThat

@RunWith(SpringRunner::class)
@SpringBootTest
class MultiplicationServiceTest {

    @MockBean
    lateinit var randomGeneratorService: RandomGeneratorService

    @MockBean
    lateinit var multiplicationService: MultiplicationService

    @Test
    fun createRandomMultiplicationTest() {
        // given (our mocked Random Generator service will return first 50, then 30)
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

        // when
        val multiplication = multiplicationService.createRandomMultiplication()

        // then
        assertThat(multiplication.factorA).isEqualTo(50)
        assertThat(multiplication.factorB).isEqualTo(30)
        assertThat(multiplication.result).isEqualTo(1500)
    }

}