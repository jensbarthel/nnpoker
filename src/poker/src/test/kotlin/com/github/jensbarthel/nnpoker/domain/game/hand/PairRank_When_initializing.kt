package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Face.*
import com.github.jensbarthel.nnpoker.domain.game.deck.Suit.DIAMONDS
import com.github.jensbarthel.nnpoker.domain.game.deck.Suit.SPADES
import com.github.jensbarthel.nnpoker.domain.game.deck.of
import org.amshove.kluent.`should throw`
import org.amshove.kluent.invoking
import org.junit.jupiter.api.TestFactory
import util.parameterizedTest

class PairRank_When_initializing {
    @TestFactory
    fun `Then matching cards must contain given face pair`() = parameterizedTest(
        Pair(emptySet(), ACE),
        Pair(setOf(ACE of SPADES, TWO of SPADES), ACE),
        Pair(setOf(KING of SPADES, KING of DIAMONDS), ACE)
    ) { (matchingCards, pairFace) ->
        // Act & Assert
        invoking { PairRank(matchingCards, pairFace) } `should throw` IllegalArgumentException::class
    }
}