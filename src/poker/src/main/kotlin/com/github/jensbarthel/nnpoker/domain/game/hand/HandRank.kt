package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Card
import com.github.jensbarthel.nnpoker.domain.game.deck.Face
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.PAIR

sealed class HandRank(val opinion: Opinion, val matchingCards: Set<Card>) : Comparable<HandRank> {
    enum class Opinion {
        NONE,
        HIGH_CARD,
        PAIR,
        DOUBLE_PAIR,
        TRIPS,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        QUADS,
        STRAIGHT_FLUSH,
        ROYAL_FLUSH
    }

    companion object {
        val NONE = NoneRank
    }

    object NoneRank : HandRank(Opinion.NONE, emptySet()) {
        override fun compareTo(other: HandRank) = opinion.compareTo(other.opinion)
    }
}

class BasicRank(opinion: Opinion, matchingCards: Set<Card>) : HandRank(opinion, matchingCards) {
    override fun compareTo(other: HandRank): Int = opinion.compareTo(other.opinion)
}

class HighCardRank(matchingCards: Set<Card>) : HandRank(Opinion.HIGH_CARD, matchingCards) {
    override fun compareTo(other: HandRank): Int {
        return when (other) {
            is HighCardRank -> compareHighestCards(
                matchingCards.sortedDescending(),
                other.matchingCards.sortedDescending()
            )
            else -> opinion.compareTo(other.opinion)
        }
    }

    private fun compareHighestCards(cards: List<Card>, otherCards: List<Card>): Int {
        require(cards.size == otherCards.size) { "Can only compare cards of same amount" }
        return if (cards.isEmpty()) {
            0
        } else {
            when (val result = cards.first().compareTo(otherCards.first())) {
                0 -> compareHighestCards(cards.drop(1), otherCards.drop(1))
                else -> result
            }
        }
    }
}

class PairRank(matchingCards: Set<Card>, val pairFace: Face) : HandRank(PAIR, matchingCards) {
    override fun compareTo(other: HandRank): Int {
        return when (other) {
            is PairRank -> pairFace.compareTo(other.pairFace)
            else -> opinion.compareTo(other.opinion)
        }
    }

}