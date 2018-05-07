package ru.posol.microservice.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.posol.microservice.gamification.domain.LeaderBoardRow
import ru.posol.microservice.gamification.repository.ScoreCardRepository
import ru.posol.microservice.gamification.service.LeaderBoardServiceIml


class LeaderBoardServiceImlTest {

    lateinit var leaderBoardService: LeaderBoardServiceIml

    @Mock
    lateinit var scoreCardRepository: ScoreCardRepository

    @Before
    fun setUp() {
        // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this)
        leaderBoardService = LeaderBoardServiceIml(scoreCardRepository);
    }

    @Test
    fun retrieveLeaderBoardTest() {
        // given
        val userId = 1L
        val leaderRow1 = LeaderBoardRow(userId, 300L)
        val expectedLeaderBoard = listOf(leaderRow1)
        given(scoreCardRepository.findFirst10()).willReturn(expectedLeaderBoard)

        // when
        val leaderBoard = leaderBoardService.getCurrentLeaderBoard()

        // then
        assertThat(leaderBoard).isEqualTo(expectedLeaderBoard)
    }


}