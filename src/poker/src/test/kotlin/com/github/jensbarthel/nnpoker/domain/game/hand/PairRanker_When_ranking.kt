package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Face.*
import com.github.jensbarthel.nnpoker.domain.game.deck.Suit.*
import com.github.jensbarthel.nnpoker.domain.game.deck.of
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.*
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should contain all`
import org.amshove.kluent.shouldHaveSize
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import util.parameterizedTest

class PairRanker_When_ranking {
    private val quads = setOf(ACE of SPADES, JACK of DIAMONDS, ACE of DIAMONDS, ACE of CLUBS, ACE of HEARTS)

    private val fullHouse = setOf(ACE of SPADES, JACK of HEARTS, ACE of DIAMONDS, ACE of CLUBS, JACK of DIAMONDS)

    private val trips = setOf(ACE of SPADES, QUEEN of HEARTS, ACE of DIAMONDS, ACE of CLUBS, JACK of DIAMONDS)

    private val threeDoublePairs =
        setOf(ACE of SPADES, QUEEN of HEARTS, QUEEN of DIAMONDS, ACE of CLUBS, JACK of DIAMONDS)

    private val doublePairs = setOf(ACE of SPADES, QUEEN of HEARTS, QUEEN of DIAMONDS, ACE of CLUBS, JACK of DIAMONDS)

    private val pair = setOf(NINE of SPADES, QUEEN of HEARTS, QUEEN of DIAMONDS, ACE of CLUBS, JACK of DIAMONDS)

    private val none = setOf(
        NINE of SPADES,
        THREE of HEARTS,
        QUEEN of DIAMONDS,
        ACE of CLUBS,
        JACK of DIAMONDS,
        EIGHT of SPADES,
        SEVEN of CLUBS
    )

    @TestFactory
    fun `If hand contains match Then it is ranked correctly`(): List<DynamicTest> {
        return parameterizedTest(
            Triple(pair + (EIGHT of SPADES) + (SEVEN of CLUBS), PAIR, pair),
            Triple(doublePairs + (TWO of SPADES) + (SEVEN of CLUBS), DOUBLE_PAIR, doublePairs),
            Triple(threeDoublePairs + (JACK of SPADES) + (SEVEN of CLUBS), DOUBLE_PAIR, threeDoublePairs),
            Triple(trips + (THREE of SPADES) + (SEVEN of CLUBS), TRIPS, trips),
            Triple(fullHouse + (THREE of SPADES) + (THREE of CLUBS), FULL_HOUSE, fullHouse),
            Triple(quads + (THREE of SPADES) + (EIGHT of CLUBS), QUADS, quads)

        )
        { (cards, expectedOpinion, finalHandSuggestion) ->
            // Act
            val rank = PairingRanker().rank(cards)

            // Assert
            rank.opinion `should be` expectedOpinion
            rank.matchingCards shouldHaveSize 5
            rank.matchingCards `should contain all` finalHandSuggestion
        }
    }

    @Test
    fun `If hand contains no match Then none is ranked`() {
        // Act
        val rank = PairingRanker().rank(none)

        // Assert
        rank.opinion `should be` NONE
        rank.matchingCards shouldHaveSize 0
    }
}