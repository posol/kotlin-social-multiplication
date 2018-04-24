package ru.posol.socialmultiplication.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.posol.socialmultiplication.domain.Multiplication
import ru.posol.socialmultiplication.domain.MultiplicationResultAttempt
import ru.posol.socialmultiplication.domain.User

class MultiplicationServiceImplTest {

    @Mock
    lateinit var randomGeneratorService: RandomGeneratorService

    lateinit var multiplicationServiceImpl: MultiplicationServiceImpl

    @Before
    fun setUp() {
        // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this)
        multiplicationServiceImpl = MultiplicationServiceImpl(randomGeneratorService);
    }

    @Test
    fun createRandomMultiplicationTest() {
        // given (our mocked Random Generator service will return first 50, then 30)
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

        // when
        val multiplication = multiplicationServiceImpl.createRandomMultiplication()

        // then
        assertThat(multiplication.factorA).isEqualTo(50)
        assertThat(multiplication.factorB).isEqualTo(30)
        assertThat(multiplication.result).isEqualTo(1500)
    }

    @Test
    fun checkCorrectAttemptTest() {
        // given
        val multiplication = Multiplication(50, 60)
        val user = User("posol")
        val attempt = MultiplicationResultAttempt(user, multiplication, 3000)

        // when
        val attemptResult = multiplicationServiceImpl.checkAttempt(attempt)

        // assert
        assertThat(attemptResult).isTrue()
    }

    @Test
    fun checkWrongAttemptTest() {
        // given
        val multiplication = Multiplication(50, 60)
        val user = User("posol")
        val attempt = MultiplicationResultAttempt(user, multiplication, 3010)

        // when
        val attemptResult = multiplicationServiceImpl.checkAttempt(attempt)

        // assert
        assertThat(attemptResult).isFalse()
    }

}