package ru.posol.microservice.multiplication.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import ru.posol.microservice.multiplication.domain.Multiplication
import ru.posol.microservice.multiplication.domain.MultiplicationResultAttempt
import ru.posol.microservice.multiplication.domain.User
import ru.posol.microservice.multiplication.event.EventDispatcher
import ru.posol.microservice.multiplication.event.MultiplicationSolvedEvent
import ru.posol.microservice.multiplication.repository.MultiplicationRepository
import ru.posol.microservice.multiplication.repository.MultiplicationResultAttemptRepository
import ru.posol.microservice.multiplication.repository.UserRepository
import javax.transaction.Transactional

@Service
class MultiplicationServiceImpl(
        @Autowired
        val randomGeneratorService: RandomGeneratorService,
        @Autowired
        val attemptRepository: MultiplicationResultAttemptRepository,
        @Autowired
        val userRepository: UserRepository,
        @Autowired
        val multiplicationRepository: MultiplicationRepository,
        @Autowired
        val eventDispatcher: EventDispatcher

) : MultiplicationService {

    override fun createRandomMultiplication(): Multiplication {
        val factA = randomGeneratorService.generateRandomFactor()
        val factB = randomGeneratorService.generateRandomFactor()
        return Multiplication(factA, factB)
    }

    @Transactional
    override fun checkAttempt(attempt: MultiplicationResultAttempt): Boolean {
        // Avoids 'hack' attempts
        Assert.isTrue(!attempt.correct, "You can't send an attempt marked as correct!!");

        // Check if the user already exists for that alias
        val user: User? = userRepository.findByAlias(attempt.user.alias)

        //search multiplication in DB
        val multiplication = multiplicationRepository.findByFactorAAndFactorB(attempt.multiplication.factorA, attempt.multiplication.factorB)

        // Checks if it's correct
        val isCorrect = attempt.resultAttempt == attempt.multiplication.factorA * attempt.multiplication.factorB

        // Creates a copy, now setting the 'correct' field accordingly
        val checkedAttempt = attempt.copy(user = user ?: attempt.user, multiplication =
                multiplication ?: attempt.multiplication, correct = isCorrect)

        // Stores the attempt
        attemptRepository.save(checkedAttempt);

        // Communicates the result via Event
        eventDispatcher.send(MultiplicationSolvedEvent(checkedAttempt.id, checkedAttempt.user.id, checkedAttempt.correct))

        return isCorrect
    }

    override fun getStatsForUser(userAlias: String) = attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias)



}