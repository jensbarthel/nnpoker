package com.github.jensbarthel.nnpoker.domain.game.deck

import com.github.jensbarthel.nnpoker.domain.game.deck.Face.*
import com.github.jensbarthel.nnpoker.domain.game.deck.Suit.*
import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.Test

class Cards_When_grouping_by_suit {


    @Test
    fun `Then cards are grouped correctly`() {
        // Arrange
        val cards = setOf(ACE of SPADES, EIGHT of SPADES, THREE of HEARTS, EIGHT of DIAMONDS)

        // Act
        val byRank = cards.bySuit()

        // Assert
        byRank[SPADES] `should equal` listOf(ACE of SPADES, EIGHT of SPADES)
        byRank[HEARTS] `should equal` listOf(THREE of HEARTS)
        byRank[DIAMONDS] `should equal` listOf(EIGHT of DIAMONDS)
    }
}