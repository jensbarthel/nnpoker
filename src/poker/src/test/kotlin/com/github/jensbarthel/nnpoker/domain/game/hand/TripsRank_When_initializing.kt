package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Face.*
import com.github.jensbarthel.nnpoker.domain.game.deck.Suit.*
import com.github.jensbarthel.nnpoker.domain.game.deck.of
import org.amshove.kluent.AnyException
import org.amshove.kluent.`should not throw`
import org.amshove.kluent.`should throw`
import org.amshove.kluent.invoking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import util.parameterizedTest

class TripsRank_When_initializing {
    @TestFactory
    fun `If matching cards do not contain trips Then throw`() = parameterizedTest(
        Pair(emptySet(), ACE),
        Pair(setOf(ACE of SPADES, TWO of SPADES), ACE),
        Pair(setOf(ACE of SPADES, ACE of DIAMONDS, TWO of SPADES), ACE),
        Pair(setOf(KING of SPADES, KING of DIAMONDS), ACE)
    ) { (matchingCards, tripsFace) ->
        // Act & Assert
        invoking { TripsRank(matchingCards, tripsFace) } `should throw` IllegalArgumentException::class
    }

    @Test
    fun `If matching cards contain trips Then initialize`() {
        // Arrange
        val trips = setOf(ACE of SPADES, ACE of DIAMONDS, ACE of HEARTS)

        // Act & Assert
        invoking {TripsRank(trips, ACE)} `should not throw` AnyException
    }
}