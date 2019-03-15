package domain.game.deck

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Rank_When_comparing {

    companion object {
        @JvmStatic
        private fun orderedParamters() = Stream.of(
            Arguments.of(Rank.TWO, Rank.THREE),
            Arguments.of(Rank.THREE, Rank.FOUR),
            Arguments.of(Rank.FOUR, Rank.FIVE),
            Arguments.of(Rank.FIVE, Rank.SIX),
            Arguments.of(Rank.SIX, Rank.SEVEN),
            Arguments.of(Rank.SEVEN, Rank.EIGHT),
            Arguments.of(Rank.EIGHT, Rank.NINE),
            Arguments.of(Rank.NINE, Rank.TEN),
            Arguments.of(Rank.TEN, Rank.JACK),
            Arguments.of(Rank.JACK, Rank.QUEEN),
            Arguments.of(Rank.QUEEN, Rank.KING),
            Arguments.of(Rank.KING, Rank.ACE)
        )
    }

    @ParameterizedTest
    @MethodSource("orderedParamters")
    fun `Then values are in order`(smallerRank: Rank, greaterRank: Rank) {
        // Assert
        Assertions.assertTrue { smallerRank < greaterRank }
    }
}