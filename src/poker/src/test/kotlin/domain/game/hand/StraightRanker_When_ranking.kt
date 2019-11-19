package domain.game.hand

import domain.game.deck.Rank.*
import domain.game.deck.Suit.CLUBS
import domain.game.deck.Suit.DIAMONDS
import domain.game.deck.of
import domain.game.hand.HandRank.Opinion.STRAIGHT
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should contain same`
import org.junit.jupiter.api.Test

class StraightRanker_When_ranking {

    @Test
    fun `If hand contains straight Then a straight is ranked`() {
        // Arrange
        val straight = setOf(
            TWO of DIAMONDS,
            THREE of DIAMONDS,
            FOUR of DIAMONDS,
            FIVE of DIAMONDS,
            ACE of DIAMONDS
        )
        val hand = straight + setOf(EIGHT of CLUBS,NINE of CLUBS)

        // Act
        val rank = StraightRanker().rank(hand)

        // Assert
        rank.opinion `should be` STRAIGHT
        rank.matchingCards `should contain same` straight
    }
}