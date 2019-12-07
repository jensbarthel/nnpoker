package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Card
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Companion.NONE
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.*
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.amshove.kluent.`should equal`
import org.amshove.kluent.`should throw`
import org.amshove.kluent.invoking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.extension.ExtendWith
import util.parameterizedTest
import java.lang.IllegalArgumentException

@ExtendWith(MockKExtension::class)
class CompoundRanker_When_ranking {
    @InjectMockKs
    private lateinit var compoundRanker: CompoundRanker

    @MockK
    private lateinit var straightFlushRanker: StraightFlushRanker

    @MockK
    private lateinit var flushRanker: StraightFlushRanker

    @MockK
    private lateinit var pairingRanker: PairingRanker

    @MockK
    private lateinit var straightRanker: StraightRanker

    @MockK
    private lateinit var highCardRanker: HighCardRanker

    @MockK
    private lateinit var cards: Set<Card>

    @MockK
    private lateinit var matchingCards: Set<Card>


    private val straightFlush: Set<Pair<HandRanker, Opinion>>
        get() = setOf(
            Pair(highCardRanker, HIGH_CARD),
            Pair(straightRanker, STRAIGHT),
            Pair(flushRanker, FLUSH),
            Pair(straightFlushRanker, STRAIGHT_FLUSH)
        )

    private val fullHouse: Set<Pair<HandRanker, Opinion>>
        get() = setOf(
            Pair(highCardRanker, HIGH_CARD),
            Pair(pairingRanker, FULL_HOUSE),
            Pair(flushRanker, FLUSH)
        )

    private val highCard: Set<Pair<HandRanker, Opinion>>
        get() = setOf(
            Pair(highCardRanker, HIGH_CARD)
        )

    @BeforeEach
    fun setup() {
        setRankersToNone()
    }

    private fun setRankersToNone() {
        setOf(straightFlushRanker, pairingRanker, flushRanker, straightRanker, highCardRanker).forEach {
            every { it.rank(any()) } returns NONE
        }
    }

    @Test
    fun `If all rankers rank none Then throw`() {
        // Act & Assert
        invoking { compoundRanker.rank(cards) } `should throw` IllegalArgumentException::class
    }

    @TestFactory
    fun `If multiple rankers have opinions Then the highest rank is chosen`() = parameterizedTest(
        Pair(straightFlush, STRAIGHT_FLUSH),
        Pair(fullHouse, FULL_HOUSE),
        Pair(highCard, HIGH_CARD)
    ) { (rankersAndOpinions, expectedOpinion) ->
        // Arrange
        clearAllMocks()
        setRankersToNone()
        setupSpecificRankers(rankersAndOpinions, cards)

        // Act
        val compoundRank = compoundRanker.rank(cards)

        // Assert
        compoundRank.opinion `should equal` expectedOpinion
        compoundRank.matchingCards `should equal` matchingCards
    }

    private fun setupSpecificRankers(
        it: Set<Pair<HandRanker, Opinion>>,
        cards: Set<Card>
    ) = it.forEach { (ranker, opinion) ->
        every { ranker.rank(cards) }.returns(mockk<HandRank>().also { rankMock ->
            every { rankMock.opinion } returns opinion
            every { rankMock.matchingCards } returns matchingCards
        })
    }
}