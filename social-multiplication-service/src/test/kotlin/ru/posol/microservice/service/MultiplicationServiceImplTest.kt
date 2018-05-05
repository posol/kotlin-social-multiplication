package ru.posol.microservice.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.posol.microservice.multiplication.domain.Multiplication
import ru.posol.microservice.multiplication.domain.MultiplicationResultAttempt
import ru.posol.microservice.multiplication.domain.User
import ru.posol.microservice.multiplication.event.EventDispatcher
import ru.posol.microservice.multiplication.event.MultiplicationSolvedEvent
import ru.posol.microservice.multiplication.repository.MultiplicationRepository
import ru.posol.microservice.multiplication.repository.MultiplicationResultAttemptRepository
import ru.posol.microservice.multiplication.repository.UserRepository
import ru.posol.microservice.multiplication.service.MultiplicationServiceImpl
import ru.posol.microservice.multiplication.service.RandomGeneratorService


class MultiplicationServiceImplTest {

    lateinit var multiplicationServiceImpl: MultiplicationServiceImpl

    @Mock
    lateinit var randomGeneratorService: RandomGeneratorService

    @Mock
    lateinit var attemptRepository: MultiplicationResultAttemptRepository

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var  multiplicationRepository: MultiplicationRepository

    @Mock
    lateinit var  eventDispatcher: EventDispatcher

    @Before
    fun setUp() {
        // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this)
        multiplicationServiceImpl = MultiplicationServiceImpl(randomGeneratorService, attemptRepository, userRepository, multiplicationRepository, eventDispatcher);
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
    }

    @Test
    fun checkCorrectAttemptTest() {
        // given
        val multiplication = Multiplication(50, 60)
        val user = User(alias = "posol")
        val attempt = MultiplicationResultAttempt(user = user, multiplication = multiplication,
                resultAttempt = 3000, correct = false)
        val verifiedAttempt = attempt.copy(correct = true)
        val event = MultiplicationSolvedEvent(verifiedAttempt.id, verifiedAttempt.user.id, verifiedAttempt.correct)
        given(userRepository.findByAlias("posol")).willReturn(null);
        given(multiplicationRepository.findByFactorAAndFactorB(50,60)).willReturn(null);

        // when
        val attemptResult = multiplicationServiceImpl.checkAttempt(attempt)

        // then
        assertThat(attemptResult).isTrue()
        // real call is not executed, only verifying that the mock objects are called with those arguments
        verify(attemptRepository).save(verifiedAttempt)
        verify(eventDispatcher).send(eq(event))
    }

    @Test
    fun checkWrongAttemptTest() {
        // given
        val multiplication = Multiplication(50, 60)
        val user = User(alias = "posol")
        val attempt = MultiplicationResultAttempt(user = user, multiplication = multiplication,
                resultAttempt = 3010, correct = false)
        val event = MultiplicationSolvedEvent(attempt.id, attempt.user.id, attempt.correct)
        given(userRepository.findByAlias("posol")).willReturn(null);
        given(multiplicationRepository.findByFactorAAndFactorB(50,60)).willReturn(null);

        // when
        val attemptResult = multiplicationServiceImpl.checkAttempt(attempt)

        // then
        assertThat(attemptResult).isFalse()
        // real call is not executed, only verifying that the mock objects are called with those arguments
        verify(attemptRepository).save(attempt)
        verify(eventDispatcher).send(eq(event))
    }

    @Test
    fun retrieveStatsTest() {
        // given
        val multiplication = Multiplication(50, 60)
        val user = User(alias = "posol")
        val attempt1 = MultiplicationResultAttempt(user = user, multiplication = multiplication,
                resultAttempt = 3010, correct = false)
        val attempt12 = attempt1.copy(resultAttempt = 3051)
        val latestAttempts = listOf(attempt1,attempt12)
        given(userRepository.findByAlias("posol")).willReturn(null);
        given(multiplicationRepository.findByFactorAAndFactorB(50,60)).willReturn(null);
        given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("posol")).willReturn(latestAttempts);

        // when
        val latestAttemptsResult = multiplicationServiceImpl.getStatsForUser("posol")

        // then
        assertThat(latestAttemptsResult).isEqualTo(latestAttempts)

    }

}