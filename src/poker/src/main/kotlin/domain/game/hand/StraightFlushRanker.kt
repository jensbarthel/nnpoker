package domain.game.hand

import domain.game.deck.Card
import domain.game.deck.Rank.ACE
import domain.game.hand.HandRank.Companion.NONE
import domain.game.hand.HandRank.Opinion

class StraightFlushRanker(private val flushRanker: FlushRanker, private val straightRanker: StraightRanker) :
    HandRanker {
    override fun rank(cards: Set<Card>): HandRank {
        val intersectedCards = intersectSubMatcherCards(cards)
        return when {
            intersectedCards.size != 5 -> NONE
            intersectedCards.last().rank == ACE -> HandRank(Opinion.ROYAL_FLUSH, intersectedCards)
            else -> HandRank(Opinion.STRAIGHT_FLUSH, intersectedCards)
        }
    }

    private fun intersectSubMatcherCards(cards: Set<Card>): Set<Card> =
        flushRanker.rank(cards).matchingCards intersect straightRanker.rank(cards).matchingCards
}