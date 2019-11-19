package domain.game.hand

import domain.game.deck.Rank.*
import domain.game.deck.Suit.*
import domain.game.deck.of
import domain.game.hand.HandRank.Opinion.FLUSH
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should contain same`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FlushRanker_When_ranking {
    private lateinit var ranker: FlushRanker

    @BeforeEach
    fun setup() {
        ranker = FlushRanker()
    }

    @Test
    fun `If hand is a flush Then a flush is evaluated`() {
        // Arrange
        val flush = setOf(
            FIVE of CLUBS,
            SIX of CLUBS,
            JACK of CLUBS,
            TWO of CLUBS,
            KING of CLUBS
        )


        val hand = flush + setOf(
            SIX of HEARTS,
            TWO of DIAMONDS
        )

        // Act
        val rank = ranker.rank(hand)

        // Assert
        rank.opinion `should be` FLUSH
        rank.matchingCards `should contain same` flush
    }

    @Test
    fun `If hand is not a flush Then none is evaluated`() {
        // Arrange
        val flushHand = setOf(
            FIVE of CLUBS,
            SIX of SPADES,
            JACK of CLUBS,
            TWO of CLUBS,
            KING of CLUBS
        )

        // Act
        val rank = ranker.rank(flushHand)

        // Assert
        rank `should be` HandRank.NONE
    }
}