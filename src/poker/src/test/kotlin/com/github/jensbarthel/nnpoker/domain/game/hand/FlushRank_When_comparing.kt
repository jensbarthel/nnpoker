package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Face.*
import com.github.jensbarthel.nnpoker.domain.game.deck.Suit.*
import com.github.jensbarthel.nnpoker.domain.game.deck.of
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.*
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be greater than`
import org.amshove.kluent.`should be less than`
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import util.parameterizedTest

class FlushRank_When_comparing {

    private val aceHighFlushThreeKicker =
        setOf(ACE of DIAMONDS, QUEEN of DIAMONDS, TEN of DIAMONDS, SEVEN of DIAMONDS, THREE of DIAMONDS)

    private val aceHighFlushTwoKicker =
        setOf(ACE of DIAMONDS, QUEEN of DIAMONDS, TEN of DIAMONDS, SEVEN of DIAMONDS, TWO of DIAMONDS)

    @TestFactory
    fun `If opinions are different Then they are compared`() = parameterizedTest(
        Pair(STRAIGHT, 1),
        Pair(FULL_HOUSE, -1)
    ) { (otherOpinion, expectedComparison) ->
        // Arrange
        val otherRank = BasicRank(otherOpinion, emptySet())

        // Act
        val comparison = FlushRank(aceHighFlushThreeKicker).compareTo(otherRank)

        // Assert
        comparison `should be equal to` expectedComparison
    }

    @Test
    fun `If opinion is flush Then higher flush has higher rank`() {
        // Arrange
        val higherRank = FlushRank(aceHighFlushThreeKicker)
        val lowerRank = FlushRank(aceHighFlushTwoKicker)

        // Assert
        higherRank.compareTo(lowerRank) `should be greater than` 0
        lowerRank.compareTo(higherRank) `should be less than` 0
        higherRank.compareTo(higherRank) `should be` 0
    }
}