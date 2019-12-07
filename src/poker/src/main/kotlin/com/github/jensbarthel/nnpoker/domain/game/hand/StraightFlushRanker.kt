package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Card
import com.github.jensbarthel.nnpoker.domain.game.deck.Face.ACE
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Companion.NONE
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion
import org.springframework.stereotype.Component

@Component
class StraightFlushRanker(private val flushRanker: FlushRanker, private val straightRanker: StraightRanker) :
    HandRanker {
    override fun rank(cards: Set<Card>): HandRank {
        val intersectedCards = intersectSubMatcherCards(cards)
        return when {
            intersectedCards.size != 5 -> NONE
            intersectedCards.last().face == ACE -> BasicRank(Opinion.ROYAL_FLUSH, intersectedCards)
            else -> BasicRank(Opinion.STRAIGHT_FLUSH, intersectedCards)
        }
    }

    private fun intersectSubMatcherCards(cards: Set<Card>): Set<Card> =
        flushRanker.rank(cards).matchingCards intersect straightRanker.rank(cards).matchingCards
}