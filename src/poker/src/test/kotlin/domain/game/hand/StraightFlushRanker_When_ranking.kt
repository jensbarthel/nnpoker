package domain.game.hand

import domain.game.deck.Rank.*
import domain.game.deck.Suit.*
import domain.game.deck.of
import domain.game.hand.HandRank.Opinion.*
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should contain all`
import org.amshove.kluent.shouldHaveSize
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.extension.ExtendWith
import util.parameterizedTest

@ExtendWith(MockKExtension::class)
class StraightFlushRanker_When_ranking {
    @MockK
    private lateinit var straightRanker: StraightRanker

    @MockK
    private lateinit var flushRanker: FlushRanker

    private val straightFlush =
        setOf(NINE of SPADES, TEN of SPADES, JACK of SPADES, QUEEN of SPADES, KING of SPADES)

    private val royalFlush =
        setOf(TEN of SPADES, JACK of SPADES, QUEEN of SPADES, KING of SPADES, ACE of SPADES)

    private val separateFlushAndStraight = setOf(
        TWO of SPADES,
        FOUR of SPADES,
        FIVE of SPADES,
        SIX of SPADES,
        SEVEN of SPADES,
        EIGHT of DIAMONDS
    )

    @TestFactory
    fun `If hand is straight flush Then it is ranked correctly`() =
        parameterizedTest(
            Triple(straightFlush + (EIGHT of SPADES) + (SEVEN of CLUBS), STRAIGHT_FLUSH, straightFlush),
            Triple(royalFlush + (EIGHT of SPADES) + (SEVEN of CLUBS), ROYAL_FLUSH, royalFlush)
        )
        { (cards, expectedOpinion, finalHandSuggestion) ->
            // Arrange
            every { straightRanker.rank(cards) } returns HandRank(STRAIGHT, finalHandSuggestion)
            every { flushRanker.rank(cards) } returns HandRank(FLUSH, finalHandSuggestion)

            // Act
            val rank = StraightFlushRanker(flushRanker, straightRanker).rank(cards)

            // Assert
            rank.opinion `should be` expectedOpinion
            rank.matchingCards shouldHaveSize 5
            rank.matchingCards `should contain all` finalHandSuggestion
        }

    @Test
    fun `If hand contains flush and non matching straight Then rank none`() {
        // Arrange
        every { flushRanker.rank(separateFlushAndStraight) } returns HandRank(
            FLUSH,
            separateFlushAndStraight.take(5).toSet()
        )

        every { straightRanker.rank(separateFlushAndStraight) } returns HandRank(
            STRAIGHT,
            separateFlushAndStraight.drop(1).toSet()
        )

        // Act
        val rank = StraightFlushRanker(flushRanker, straightRanker).rank(separateFlushAndStraight)

        // Assert
        rank.opinion `should be` HandRank.Opinion.NONE
        rank.matchingCards shouldHaveSize 0
    }
}