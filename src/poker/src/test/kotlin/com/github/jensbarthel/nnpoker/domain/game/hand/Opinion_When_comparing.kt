package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.*
import org.amshove.kluent.`should be less than`
import org.junit.jupiter.api.TestFactory
import util.parameterizedTest

class Opinion_When_comparing {
    @TestFactory
    fun `Then order is correct`() = parameterizedTest(
        Pair(NONE, HIGH_CARD),
        Pair(HIGH_CARD, PAIR),
        Pair(PAIR, DOUBLE_PAIR),
        Pair(DOUBLE_PAIR, TRIPS),
        Pair(TRIPS, STRAIGHT),
        Pair(STRAIGHT, FLUSH),
        Pair(FLUSH, QUADS),
        Pair(QUADS, STRAIGHT_FLUSH),
        Pair(STRAIGHT_FLUSH, ROYAL_FLUSH)
    ) { (lowerRank, higherRank) ->
        // Act & Assert
        lowerRank `should be less than` higherRank
    }

    private infix fun HandRank.Opinion.`should be less than`(other: HandRank.Opinion) =
        this.ordinal `should be less than` other.ordinal
}