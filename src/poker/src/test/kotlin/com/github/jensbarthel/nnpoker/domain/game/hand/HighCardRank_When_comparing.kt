package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Face.*
import com.github.jensbarthel.nnpoker.domain.game.deck.Suit.*
import com.github.jensbarthel.nnpoker.domain.game.deck.of
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.NONE
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.PAIR
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should throw`
import org.amshove.kluent.invoking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import util.parameterizedTest

internal class HighCardRank_When_comparing {
    @TestFactory
    fun `If opinions are different Then they are compared`() = parameterizedTest(
        Pair(NONE, 1),
        Pair(PAIR, -1)
    ) { (otherOpinion, expectedComparison) ->
        // Arrange
        val otherRank = BasicRank(otherOpinion, emptySet())

        // Act
        val comparison = HighCardRank(mockk()).compareTo(otherRank)

        // Assert
        comparison `should be equal to` expectedComparison
    }

    @TestFactory
    fun `If opinions are same Then compare highest cards`() = parameterizedTest(
        Triple(
            setOf(
                ACE of HEARTS
            ), setOf(
                ACE of DIAMONDS
            ), 0
        ),
        Triple(
            setOf(
                EIGHT of SPADES,
                ACE of HEARTS
            ), setOf(
                SEVEN of CLUBS,
                ACE of DIAMONDS
            ), 1
        ),
        Triple(
            setOf(
                SIX of SPADES,
                ACE of HEARTS
            ), setOf(
                SEVEN of CLUBS,
                ACE of DIAMONDS
            ), -1
        )

    ) { (cards, otherCards, expectedComparison) ->
        // Arrange
        val highCardRank = HighCardRank(cards)
        val otherRank = HighCardRank(otherCards)

        // Act
        val comparison = highCardRank.compareTo(otherRank)

        // Assert
        comparison `should be equal to` expectedComparison
    }

    @Test
    fun `If opinions are same but matching card sizes differ Then throw`() {
        // Arrange
        val highCardRank = HighCardRank(setOf(ACE of SPADES))
        val otherRank = HighCardRank(emptySet())

        // Act & Assert
        invoking { highCardRank.compareTo(otherRank) } `should throw` IllegalArgumentException::class
    }
}