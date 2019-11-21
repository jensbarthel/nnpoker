package domain.game.hand

import domain.game.deck.Face.*
import domain.game.deck.Suit.*
import domain.game.deck.of
import domain.game.hand.HandRank.Opinion.HIGH_CARD
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should contain all`
import org.amshove.kluent.shouldHaveSize
import org.junit.jupiter.api.Test

class HighCardRanker_When_ranking {
    @Test
    fun `Then highest card is ranked`() {
        // Arrange
        val highestCards = setOf(
            FIVE of SPADES,
            QUEEN of CLUBS,
            SEVEN of HEARTS,
            EIGHT of DIAMONDS,
            ACE of HEARTS
        )

        // Act
        val rank = HighCardRanker().rank(highestCards + (TWO of SPADES) + (FOUR of DIAMONDS))

        // Assert
        rank.opinion `should be` HIGH_CARD
        rank.matchingCards shouldHaveSize 5
        rank.matchingCards `should contain all` highestCards
    }
}