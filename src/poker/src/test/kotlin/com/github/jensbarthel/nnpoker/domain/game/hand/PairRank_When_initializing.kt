package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Face.*
import com.github.jensbarthel.nnpoker.domain.game.deck.Suit
import com.github.jensbarthel.nnpoker.domain.game.deck.Suit.DIAMONDS
import com.github.jensbarthel.nnpoker.domain.game.deck.Suit.SPADES
import com.github.jensbarthel.nnpoker.domain.game.deck.of
import org.amshove.kluent.AnyException
import org.amshove.kluent.`should not throw`
import org.amshove.kluent.`should throw`
import org.amshove.kluent.invoking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import util.parameterizedTest

class PairRank_When_initializing {
    @TestFactory
    fun `If matching cards do not contain pair Then throw`() = parameterizedTest(
        Pair(emptySet(), ACE),
        Pair(setOf(ACE of SPADES, TWO of SPADES), ACE),
        Pair(setOf(KING of SPADES, KING of DIAMONDS), ACE)
    ) { (matchingCards, pairFace) ->
        // Act & Assert
        invoking { PairRank(matchingCards, pairFace) } `should throw` IllegalArgumentException::class
    }

    @Test
    fun `If matching cards contain pair Then initialize`() {
        // Act & Assert
        invoking {PairRank(setOf(ACE of DIAMONDS, ACE of Suit.HEARTS), ACE)} `should not throw` AnyException
    }
}