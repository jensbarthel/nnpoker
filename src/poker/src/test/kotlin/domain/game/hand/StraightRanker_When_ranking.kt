package domain.game.hand

import domain.game.deck.Card
import domain.game.deck.Rank.*
import domain.game.deck.Suit.*
import domain.game.deck.of
import domain.game.hand.HandRank.Opinion.STRAIGHT
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should contain same`
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class StraightRanker_When_ranking {

    companion object {
        @JvmStatic
        fun straights() = Stream.of(
            Arguments.of(
                setOf(
                    TWO of DIAMONDS,
                    THREE of HEARTS,
                    FOUR of DIAMONDS,
                    FIVE of CLUBS,
                    ACE of DIAMONDS
                )
            ),
            Arguments.of(
                setOf(
                    NINE of SPADES,
                    TEN of SPADES,
                    JACK of CLUBS,
                    QUEEN of HEARTS,
                    KING of HEARTS
                )
            )
        )
    }

    @ParameterizedTest
    @MethodSource("straights")
    fun `If hand contains straight Then a straight is ranked`(straight: Set<Card>) {
        // Arrange
        val hand = straight + setOf(EIGHT of CLUBS, NINE of CLUBS)

        // Act
        val rank = StraightRanker().rank(hand)

        // Assert
        rank.opinion `should be` STRAIGHT
        rank.matchingCards `should contain same` straight
    }
}