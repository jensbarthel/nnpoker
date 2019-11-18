package domain.game.hand

import domain.game.hand.RankDescription.FLUSH
import domain.game.deck.Rank.*
import domain.game.deck.Suit.*
import domain.game.deck.of
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Ranker_When_ranking {
    private lateinit var ranker: Ranker

    @BeforeEach
    fun setup() {
        ranker = Ranker()
    }

    companion object {
        private val twoOfClubs = TWO of CLUBS
        private val fiveOfClubs = FIVE of CLUBS
        private val sixOfHearts = SIX of HEARTS
        private val sixOfClubs = SIX of CLUBS
        private val aceOfSpades = ACE of SPADES
        private val kingOfDiamonds = KING of DIAMONDS
        private val threeOfHearts = THREE of HEARTS
        private val jackOfClubs = JACK of CLUBS
        private val kingOfClubs = KING of CLUBS
    }

    @Test
    fun `If hand is a flush Then a flush is evaluated`() {
        // Arrange
        val flushHand = setOf(
            fiveOfClubs,
            sixOfClubs,
            jackOfClubs,
            twoOfClubs,
            kingOfClubs
        )

        // Act
        val rank = ranker.rank(flushHand)

        // Assert
        rank `should be` FLUSH
    }
}