package domain.game

import domain.game.deck.Card
import domain.game.deck.Rank
import domain.game.deck.Suit
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Ranker_When_ranking {
    private lateinit var ranker: Ranker

    @BeforeEach
    fun setup() {
        ranker = Ranker()
    }

    private val fiveOfClubs = Card(Suit.CLUBS, Rank.FIVE)
    private val sixOfHearts = Card(Suit.HEARTS, Rank.SIX)
    private val sixOfClubs = Card(Suit.CLUBS, Rank.SIX)
    private val aceOfSpades = Card(Suit.SPADES, Rank.ACE)
    private val kingOfDiamonds= Card(Suit.DIAMONDS, Rank.KING)
    private val threeOfHearts= Card(Suit.HEARTS, Rank.THREE)
    private val jackOfClubs= Card(Suit.CLUBS, Rank.JACK)

    @Test
    fun `If hand only contains one pair Then ranker evaluates one pair`() {
        // Arrange
        val hand = listOf(fiveOfClubs, sixOfHearts)

        val communityCards = listOf(
            sixOfClubs,
            aceOfSpades,
            kingOfDiamonds,
            threeOfHearts,
            jackOfClubs
        )

        // Act
        ranker.evaluate(listOf(
            hand, communityCards
        ).flatten().toSet())

        // Assert
    }
}