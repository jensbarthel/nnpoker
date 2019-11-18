package domain.game

import domain.game.deck.Card
import domain.game.deck.Rank
import domain.game.deck.Suit
import domain.game.hand.Hand
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Hand_When_evaluating {
    private val twoOfClubs = Card(Suit.CLUBS, Rank.TWO)
    private val threeOfHearts = Card(Suit.HEARTS, Rank.THREE)
    private val fourOfDiamonds = Card(Suit.DIAMONDS, Rank.FOUR)
    private val fiveOfClubs = Card(Suit.CLUBS, Rank.FIVE)
    private val sixOfHearts = Card(Suit.HEARTS, Rank.SIX)
    private val sixOfClubs = Card(Suit.CLUBS, Rank.SIX)
    private val sevenOfDiamonds = Card(Suit.DIAMONDS, Rank.SEVEN)
    private val eightOfClubs = Card(Suit.CLUBS, Rank.EIGHT)

    private val jackOfClubs = Card(Suit.CLUBS, Rank.JACK)
    private val queenOfHearts = Card(Suit.HEARTS, Rank.QUEEN)
    private val kingOfDiamonds = Card(Suit.DIAMONDS, Rank.KING)
    private val aceOfSpades = Card(Suit.SPADES, Rank.ACE)

    @Test
    fun `If hand contains baby straight Then hand evaluates straight`() {
        // Arrange
        val hand = Hand(
            setOf(
                aceOfSpades,
                twoOfClubs,
                threeOfHearts,
                fourOfDiamonds,
                fiveOfClubs,
                eightOfClubs,
                jackOfClubs
            )
        )

        // Act & assert
        Assertions.assertTrue { hand.isStraight() }
    }

    @Test
    fun `If hand contains regular straight Then hand evaluates straight`() {
        // Arrange
        val hand = Hand(
            setOf(
                threeOfHearts,
                fourOfDiamonds,
                fiveOfClubs,
                sixOfClubs,
                sevenOfDiamonds,
                jackOfClubs,
                queenOfHearts
            )
        )

        // Act & assert
        Assertions.assertTrue { hand.isStraight() }
    }

    @Test
    fun `If hand does not contain straight Then hand does not evaluate straight`() {
        // Arrange
        val hand = Hand(
            setOf(
                queenOfHearts,
                kingOfDiamonds,
                twoOfClubs,
                threeOfHearts,
                sixOfClubs,
                sevenOfDiamonds,
                eightOfClubs
            )
        )

        // Act & assert
        Assertions.assertFalse { hand.isStraight() }
    }
}