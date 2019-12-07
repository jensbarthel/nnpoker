package com.github.jensbarthel.nnpoker.domain.game

import com.github.jensbarthel.nnpoker.domain.game.hand.CompoundRanker
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.PAIR
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.STRAIGHT
import com.github.jensbarthel.nnpoker.domain.game.hand.HoleCardPair
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class RankingService_When_ranking {

    @InjectMockKs
    private lateinit var rankingService: RankingService

    @MockK
    private lateinit var compoundRanker: CompoundRanker

    @MockK
    private lateinit var communityCards: CommunityCards

    @Test
    fun `Then return highest rank`() {
        // Arrange
        val lowerRankingHoleCards = mockk<HoleCardPair>()
        val higherRankingHoleCards = mockk<HoleCardPair>()
        val pairRank = mockk<HandRank>().also {
            every { it.opinion } returns PAIR
        }
        val straightRank = mockk<HandRank>().also {
            every { it.opinion } returns STRAIGHT
        }

        every { compoundRanker.rank(communityCards + lowerRankingHoleCards) } returns pairRank
        every { compoundRanker.rank(communityCards + higherRankingHoleCards) } returns straightRank

        // Act
        val winningHand = rankingService.rankHands(communityCards, setOf(lowerRankingHoleCards, higherRankingHoleCards))

        // Assert
        winningHand `should equal` Pair(higherRankingHoleCards, straightRank)
    }

    fun `If winning rank is disputed Then highest ranking hand of top hands is returned`() {

    }
}