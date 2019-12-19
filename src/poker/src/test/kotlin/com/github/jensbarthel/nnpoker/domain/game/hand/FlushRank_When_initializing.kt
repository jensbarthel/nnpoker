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

class FlushRank_When_initializing {
    @Test
    fun `If matching cards do not contain flush Then throw`() {
        // Arrange
        val matchingCards =
            setOf(ACE of SPADES, QUEEN of DIAMONDS, TEN of DIAMONDS, SEVEN of DIAMONDS, THREE of DIAMONDS)

        // Act & Assert
        invoking { FlushRank(matchingCards) } `should throw` IllegalArgumentException::class
    }

    @Test
    fun `If matching cards do are of wrong amount^ Then throw`() {
        // Arrange
        val matchingCards =
            setOf(ACE of DIAMONDS, QUEEN of DIAMONDS, TEN of DIAMONDS, SEVEN of DIAMONDS)

        // Act & Assert
        invoking { FlushRank(matchingCards) } `should throw` IllegalArgumentException::class
    }

    @Test
    fun `If matching cards contain trips Then initialize`() {
        // Arrange
        val matchingCards =
            setOf(ACE of DIAMONDS, QUEEN of DIAMONDS, TEN of DIAMONDS, SEVEN of DIAMONDS, THREE of DIAMONDS)

        // Act & Assert
        invoking { FlushRank(matchingCards) } `should not throw` AnyException
    }
}