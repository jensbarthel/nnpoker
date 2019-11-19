package domain.game.hand

import domain.game.deck.Rank.*
import domain.game.deck.Suit.*
import domain.game.deck.of
import domain.game.hand.HandRank.Companion.NONE
import domain.game.hand.HandRank.Opinion.STRAIGHT
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should contain same`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import util.parameterizedTest

class StraightRanker_When_ranking {
    @TestFactory
    fun `If hand contains straight Then a straight is ranked`() = parameterizedTest(
        setOf(
            TWO of DIAMONDS,
            THREE of HEARTS,
            FOUR of DIAMONDS,
            FIVE of CLUBS,
            ACE of DIAMONDS
        ), setOf(
            NINE of SPADES,
            TEN of SPADES,
            JACK of CLUBS,
            QUEEN of HEARTS,
            KING of HEARTS
        )

    ) { straight ->
        // Arrange
        val hand = straight + setOf(EIGHT of CLUBS, NINE of CLUBS)

        // Act
        val rank = StraightRanker().rank(hand)

        // Assert
        rank.opinion `should be` STRAIGHT
        rank.matchingCards `should contain same` straight
    }

    @Test
    fun `If hand contains no straight Then none is ranked`() {
        // Arrange
        val nonStraight = setOf(
            NINE of SPADES,
            THREE of SPADES,
            JACK of CLUBS,
            QUEEN of HEARTS,
            KING of HEARTS
        )

        // Act
        val rank = StraightRanker().rank(nonStraight)

        // Assert
        rank `should be` NONE
    }
}