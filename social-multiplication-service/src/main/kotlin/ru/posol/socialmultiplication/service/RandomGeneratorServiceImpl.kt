package ru.posol.socialmultiplication.service

import org.springframework.stereotype.Service
import java.util.*

@Service
class RandomGeneratorServiceImpl : RandomGeneratorService {

    companion object {
        val MINIMUM_FACTOR: Int = 11
        val MAXIMUM_FACTOR: Int = 99
    }

    val random: Random = Random()

    override fun generateRandomFactor(): Int {
        return random.nextInt((MAXIMUM_FACTOR - MINIMUM_FACTOR) + 1) + MINIMUM_FACTOR
    }
}