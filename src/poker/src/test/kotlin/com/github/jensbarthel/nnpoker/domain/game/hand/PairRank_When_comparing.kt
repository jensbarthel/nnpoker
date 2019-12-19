package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Face.*
import com.github.jensbarthel.nnpoker.domain.game.deck.Suit.*
import com.github.jensbarthel.nnpoker.domain.game.deck.of
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.DOUBLE_PAIR
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.HIGH_CARD
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be greater than`
import org.amshove.kluent.`should be less than`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import util.parameterizedTest

class PairRank_When_comparing {

    private val pairOfAces = setOf(ACE of DIAMONDS, ACE of SPADES)

    private val pairOfTwos = setOf(TWO of DIAMONDS, TWO of SPADES)

    @TestFactory
    fun `If opinions are different Then they are compared`() = parameterizedTest(
        Pair(HIGH_CARD, 1),
        Pair(DOUBLE_PAIR, -1)
    ) { (otherOpinion, expectedComparison) ->
        // Arrange
        val otherRank = mockk<HandRank>().also { every { it.opinion } returns otherOpinion }

        // Act
        val comparison = PairRank(pairOfAces, ACE).compareTo(otherRank)

        // Assert
        comparison `should be equal to` expectedComparison
    }

    @Test
    fun `If opinion is pair Then higher pair has higher rank`() {
        // Arrange
        val higherPairRank = PairRank(pairOfAces, ACE)
        val lowerPairRank = PairRank(pairOfTwos, TWO)

        // Assert
        higherPairRank.compareTo(lowerPairRank) `should be greater than` 0
        lowerPairRank.compareTo(higherPairRank) `should be less than` 0
    }

    @TestFactory
    fun `If pairs are equal Then kicker decides ranking`() = parameterizedTest(
        Triple(FOUR of CLUBS, THREE of CLUBS, 1),
        Triple(FOUR of CLUBS, FIVE of CLUBS, -1),
        Triple(FIVE of CLUBS, FIVE of DIAMONDS, 0)

    ) {(kicker, otherKicker, expected) ->
        // Arrange
        val rank = PairRank(pairOfTwos + kicker, TWO)
        val otherRank = PairRank(pairOfTwos + otherKicker, TWO)

        // Assert
        rank.compareTo(otherRank) `should be equal to` expected
    }
}