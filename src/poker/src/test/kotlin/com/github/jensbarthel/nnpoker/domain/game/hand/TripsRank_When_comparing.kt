package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Face.*
import com.github.jensbarthel.nnpoker.domain.game.deck.Suit.*
import com.github.jensbarthel.nnpoker.domain.game.deck.of
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.*
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be greater than`
import org.amshove.kluent.`should be less than`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import util.parameterizedTest

class TripsRank_When_comparing {

    private val tripsOfAces = setOf(ACE of DIAMONDS, ACE of SPADES, ACE of HEARTS)

    private val tripsOfTwos = setOf(TWO of DIAMONDS, TWO of SPADES, TWO of HEARTS)

    @TestFactory
    fun `If opinions are different Then they are compared`() = parameterizedTest(
        Pair(DOUBLE_PAIR, 1),
        Pair(STRAIGHT, -1)
    ) { (otherOpinion, expectedComparison) ->
        // Arrange
        val otherRank = BasicRank(otherOpinion, emptySet())

        // Act
        val comparison = TripsRank(tripsOfAces, ACE).compareTo(otherRank)

        // Assert
        comparison `should be equal to` expectedComparison
    }

    @Test
    fun `If opinion is trips Then higher trips has higher rank`() {
        // Arrange
        val higherTripsRank = TripsRank(tripsOfAces, ACE)
        val lowerTripsRank = TripsRank(tripsOfTwos, TWO)

        // Assert
        higherTripsRank.compareTo(lowerTripsRank) `should be greater than` 0
        lowerTripsRank.compareTo(higherTripsRank) `should be less than` 0
    }

    @TestFactory
    fun `If trips are equal Then kicker decides ranking`() = parameterizedTest(
        Triple(FOUR of CLUBS, THREE of CLUBS, 1),
        Triple(FOUR of CLUBS, FIVE of CLUBS, -1),
        Triple(FIVE of CLUBS, FIVE of DIAMONDS, 0)

    ) {(kicker, otherKicker, expected) ->
        // Arrange
        val rank = TripsRank(tripsOfTwos + kicker, TWO)
        val otherRank = TripsRank(tripsOfTwos + otherKicker, TWO)

        // Assert
        rank.compareTo(otherRank) `should be equal to` expected
    }
}