package domain.game.deck

import domain.game.deck.Rank.*
import domain.game.deck.Suit.*
import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.Test

class Cards_When_grouping_by_rank {


    @Test
    fun `Then cards are grouped correctly`() {
        // Arrange
        val cards = setOf(ACE of SPADES, EIGHT of SPADES, THREE of HEARTS, EIGHT of DIAMONDS)

        // Act
        val byRank = cards.byRank()

        // Assert
        byRank[EIGHT] `should equal` listOf(EIGHT of SPADES, EIGHT of DIAMONDS)
        byRank[ACE] `should equal` listOf(ACE of SPADES)
        byRank[THREE] `should equal` listOf(THREE of HEARTS)
    }
}