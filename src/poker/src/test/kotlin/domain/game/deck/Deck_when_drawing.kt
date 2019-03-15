package domain.game.deck

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Deck_when_drawing {

    private val deckFactory = DeckFactory()

    @Test
    fun `If a cards are drawn Then deck size is reduced`() {
        // Arrange
        val deck = deckFactory.create()
        val deckSize = deck.size

        // Act
        val drawnCards = deck.draw(deckSize)

        // Assert
        Assertions.assertEquals(0, deck.size)
        Assertions.assertEquals(deckSize, drawnCards.size)
    }
}