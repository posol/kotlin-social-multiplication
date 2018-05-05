package ru.posol.microservice.gamification.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import ru.posol.microservice.gamification.domain.LeaderBoardRow
import ru.posol.microservice.gamification.domain.ScoreCard

/**
 * Handles CRUD operations with ScoreCards
 */
interface ScoreCardRepository : CrudRepository<ScoreCard, Long> {

    /**
     * Gets the total score for a given user, being the sum of
    the scores of all his ScoreCards.
     * @param userId the id of the user for which the total
    score should be retrieved
     * @return the total score for the given user
     */
    @Query(""""SELECT SUM(s.score) FROM ru.posol.microservice.gamification.domain.ScoreCard s
                     WHERE s.userId = :userId GROUP BY s.userId""")
    fun getTotalScoreForUser(@Param("userId") userId: Long): Int

    /**
     * Retrieves a list of {@link LeaderBoardRow}s representing
    the Leader Board of users and their total score.( create new LeaderBoardRow objects for the query
    results, using the userId and the aggregation of the score for a given user)
     * @return the leader board, sorted by highest score first.
     */
    @Query("""SELECT ru.posol.microservice.gamification.domain.LeaderBoardRow(s.userId, SUM(s.score))
                    FROM  ru.posol.microservice.gamification.domain.ScoreCard s
                    GROUP BY s.userId ORDER BY SUM(s.score) DESC""")
    fun findFirst10(): List<LeaderBoardRow>

    /**
     * Retrieves all the ScoreCards for a given user,
    identified by his user id.
     * @param userId the id of the user
     * @return a list containing all the ScoreCards for the
    given user, sorted by most recent.
     */
    fun findByUserIdOrderByScoreTimestampDesc(userId: Long): List<ScoreCard>

}