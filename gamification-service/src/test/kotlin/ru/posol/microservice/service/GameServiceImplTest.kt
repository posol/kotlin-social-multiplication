package ru.posol.microservice.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.anyLong
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.posol.microservice.gamification.client.MultiplicationResultAttemptClient
import ru.posol.microservice.gamification.client.dto.MultiplicationResultAttempt
import ru.posol.microservice.gamification.domain.Badge
import ru.posol.microservice.gamification.domain.BadgeCard
import ru.posol.microservice.gamification.domain.ScoreCard
import ru.posol.microservice.gamification.repository.BadgeCardRepository
import ru.posol.microservice.gamification.repository.ScoreCardRepository
import ru.posol.microservice.gamification.service.GameServiceImpl


class GameServiceImplTest {

    lateinit var gameService: GameServiceImpl

    @Mock
    lateinit var scoreCardRepository: ScoreCardRepository

    @Mock
    lateinit var badgeCardRepository: BadgeCardRepository

    @Mock
    lateinit var multiplicationClient: MultiplicationResultAttemptClient

    @Before
    fun setUp() {
        // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this)
        gameService = GameServiceImpl(scoreCardRepository, badgeCardRepository, multiplicationClient)

        // Common given - attempt does not contain a lucky number by default
        val attempt = MultiplicationResultAttempt("posol", 20, 70, 1400, true)
        given(multiplicationClient.retrieveMultiplicationResultAttemptbyId(anyLong())).willReturn(attempt)
    }

    @Test
    fun processFirstCorrectAttemptTest() {
        // given
        val userId = 1L
        val attemptId = 8L
        val totalScore = 10
        val scoreCard = ScoreCard(userId, attemptId)
        given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore)
        // this repository will return the just-won score card
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(listOf(scoreCard))
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(emptyList())

        // when
        val iteration = gameService.newAttemptForUser(userId, attemptId, true)

        // assert - should score one card and win the badge FIRST_WON
        assertThat(iteration.score).isEqualTo(scoreCard.score)
        assertThat(iteration.badges).containsOnly(Badge.FIRST_WON)
    }

    @Test
    fun processCorrectAttemptForScoreBadgeTest() {
        // given
        val userId = 1L
        val attemptId = 29L
        val totalScore = 100
        val firstWonBadge = BadgeCard(userId = userId, badge = Badge.FIRST_WON)
        given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(totalScore)
        // this repository will return the just-won score card
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(createNScoreCards(10, userId))
        // the first won badge is already there
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(listOf(firstWonBadge))

        // when
        val (_, score, badges) = gameService.newAttemptForUser(userId, attemptId, true)

        // assert - should score one card and win the badge BRONZE
        assertThat(score).isEqualTo(ScoreCard.DEFAULT_SCORE)
        assertThat(badges).containsOnly(Badge.BRONZE_MULTIPLICATOR)
    }

    @Test
    fun processCorrectAttemptForLuckyNumberBadgeTest() {
        // given
        val userId = 1L
        val attemptId = 29L
        val totalScore = 10
        val firstWonBadge = BadgeCard(userId = userId, badge = Badge.FIRST_WON)
        given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore)
        // this repository will return the just-won score card
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId)).willReturn(createNScoreCards(1, userId))
        // the first won badge is already there
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(listOf(firstWonBadge))
        // the attempt includes the lucky number
        val attempt = MultiplicationResultAttempt("posol", 42, 10, 420, true)
        given(multiplicationClient.retrieveMultiplicationResultAttemptbyId(attemptId)).willReturn(attempt)

        // when
        val (_, score, badges) = gameService.newAttemptForUser(userId, attemptId, true)

        // assert - should score one card and win the badge LUCKY NUMBER
        assertThat(score).isEqualTo(ScoreCard.DEFAULT_SCORE)
        assertThat(badges).containsOnly(Badge.LUCKY_NUMBER)
    }

    @Test
    fun processWrongAttemptTest() {
        // given
        val userId = 1L
        val attemptId = 8L
        val totalScore = 10
        val scoreCard = ScoreCard(userId, attemptId)
        given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(totalScore)
        // this repository will return the just-won score card
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(listOf(scoreCard))
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(emptyList())

        // when
        val (_, score, badges) = gameService.newAttemptForUser(userId, attemptId, false)

        // assert - shouldn't score anything
        assertThat(score).isEqualTo(0)
        assertThat(badges).isEmpty()
    }

    @Test
    fun retrieveStatsForUserTest() {
        // given
        val userId = 1L
        val totalScore = 1000
        val badgeCard = BadgeCard(userId = userId, badge = Badge.SILVER_MULTIPLICATOR)
        given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(totalScore)
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(listOf(badgeCard))

        // when
        val (_, score, badges) = gameService.retrieveStatsForUser(userId)

        // assert - should score one card and win the badge FIRST_WON
        assertThat(score).isEqualTo(totalScore)
        assertThat(badges).containsOnly(Badge.SILVER_MULTIPLICATOR)
    }

    private fun createNScoreCards(n: Int, userId: Long): List<ScoreCard> {
        return generateSequence(0) { it + 1 }.takeWhile { it <= n }
                .map { i -> ScoreCard(userId, i.toLong()) }.toList()
    }


}