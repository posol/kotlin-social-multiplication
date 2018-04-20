package ru.posol.socialmultiplication.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.posol.socialmultiplication.domain.Multiplication

@Service
class MultiplicationServiceImpl : MultiplicationService {

    @Autowired
    lateinit var randomGeneratorService: RandomGeneratorService


    override fun createRandomMultiplication(): Multiplication {
        val factA = randomGeneratorService.generateRandomFactor()
        val factB = randomGeneratorService.generateRandomFactor()
        return Multiplication(factA,factB)
    }
}