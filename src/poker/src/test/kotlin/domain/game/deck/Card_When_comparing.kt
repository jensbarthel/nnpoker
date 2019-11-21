package domain.game.deck

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Card_When_comparing {

    companion object {
        @JvmStatic
        private fun sameRankDifferentSuits() = Stream.of(
            Arguments.of(Suit.CLUBS, Suit.DIAMONDS),
            Arguments.of(Suit.DIAMONDS, Suit.HEARTS),
            Arguments.of(Suit.HEARTS, Suit.SPADES),
            Arguments.of(Suit.SPADES, Suit.CLUBS)
        )
    }

    @ParameterizedTest
    @MethodSource("sameRankDifferentSuits")
    fun `Then suit is irrelevant`(firstSuit: Suit, secondSuit: Suit) {
        Assertions.assertTrue { Card(firstSuit, Face.KING) < Card(secondSuit, Face.ACE) }
    }


}