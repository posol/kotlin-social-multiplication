package ru.posol.socialmultiplication.service

import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory
import ru.posol.socialmultiplication.SocialMultiplicationApplication


class RandomGeneratorServiceTest {

    lateinit var randomGeneratorServiceImpl: RandomGeneratorService

    @Before
    fun setUp() {
        randomGeneratorServiceImpl = RandomGeneratorServiceImpl()
    }

    @Test
    fun generateRandomFactorIsBetweenExpectedLimits() {
        // when a good sample of randomly generated factors is generated
        val randomFactors: List<Int> = generateSequence(0) { it + 1 }.takeWhile { it <= 1000 }
                .map { randomGeneratorServiceImpl.generateRandomFactor() }.toList()

        // then all of them should be between 11 and 100 because we want a middle-complexity calculation
        val checkList: List<Int> = generateSequence(11) { it + 1 }.takeWhile { it < 100 }.toList()

        Assertions.assertThat(randomFactors).containsOnlyElementsOf(checkList)
    }

}