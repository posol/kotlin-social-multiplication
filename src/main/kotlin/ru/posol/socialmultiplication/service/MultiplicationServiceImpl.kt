package ru.posol.socialmultiplication.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.posol.socialmultiplication.domain.Multiplication

@Service
class MultiplicationServiceImpl(@Autowired val randomGeneratorService: RandomGeneratorService) : MultiplicationService {
//class MultiplicationServiceImpl @Autowired constructor( val randomGeneratorService: RandomGeneratorService) : MultiplicationService {

    override fun createRandomMultiplication(): Multiplication {
        val factA = randomGeneratorService.generateRandomFactor()
        val factB = randomGeneratorService.generateRandomFactor()
        return Multiplication(factA, factB)
    }
}