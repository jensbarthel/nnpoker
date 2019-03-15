package domain.game.deck

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DeckFactory_When_creating{
    @Test
    fun `Then deck is created correctly`() {
        // Act
        val deck = DeckFactory().create()

        // Assert
        Assertions.assertEquals(52, deck.size)
    }
}