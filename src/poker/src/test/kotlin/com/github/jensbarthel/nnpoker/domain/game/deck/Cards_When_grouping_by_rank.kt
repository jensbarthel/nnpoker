package com.github.jensbarthel.nnpoker.domain.game.deck

import com.github.jensbarthel.nnpoker.domain.game.deck.Face.*
import com.github.jensbarthel.nnpoker.domain.game.deck.Suit.*
import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.Test

class Cards_When_grouping_by_rank {


    @Test
    fun `Then cards are grouped correctly`() {
        // Arrange
        val cards = setOf(ACE of SPADES, EIGHT of SPADES, THREE of HEARTS, EIGHT of DIAMONDS)

        // Act
        val byFace = cards.byFace()

        // Assert
        byFace[EIGHT] `should equal` listOf(EIGHT of SPADES, EIGHT of DIAMONDS)
        byFace[ACE] `should equal` listOf(ACE of SPADES)
        byFace[THREE] `should equal` listOf(THREE of HEARTS)
    }
}