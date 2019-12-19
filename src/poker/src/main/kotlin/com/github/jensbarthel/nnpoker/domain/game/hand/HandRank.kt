package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Card
import com.github.jensbarthel.nnpoker.domain.game.deck.Face
import com.github.jensbarthel.nnpoker.domain.game.deck.byFace
import com.github.jensbarthel.nnpoker.domain.game.deck.bySuit
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.*

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

    override fun compareTo(other: HandRank): Int = opinion.compareTo(other.opinion)

    companion object {
        val NONE = NoneRank
    }

    object NoneRank : HandRank(Opinion.NONE, emptySet()) {
        override fun compareTo(other: HandRank) = opinion.compareTo(other.opinion)
    }

    protected fun compareKickerTo(other: HandRank): Int =
        compareHighestCards(
            matchingCards.sortedDescending(),
            other.matchingCards.sortedDescending()
        )


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

class BasicRank(opinion: Opinion, matchingCards: Set<Card>) : HandRank(opinion, matchingCards)

abstract class KickerComparingRank(opinion: Opinion, matchingCards: Set<Card>) : HandRank(opinion, matchingCards) {
    override fun compareTo(other: HandRank): Int = when (other) {
        is KickerComparingRank -> compareKickerTo(other)
        else -> super.compareTo(other)
    }
}

class HighCardRank(matchingCards: Set<Card>) : KickerComparingRank(HIGH_CARD, matchingCards)

class PairRank(matchingCards: Set<Card>, pairFace: Face) : KickerComparingRank(PAIR, matchingCards) {
    init {
        require(matchingCards.byFace()[pairFace]?.size ?: 0 == 2) { "Matching cards must contain pair face" }
    }
}

class TripsRank(matchingCards: Set<Card>, tripsFace: Face) : KickerComparingRank(TRIPS, matchingCards) {
    init {
        require(matchingCards.byFace()[tripsFace]?.size ?: 0 == 3) { "Matching cards must contain trips face" }
    }
}

class FlushRank(matchingCards: Set<Card>) : KickerComparingRank(FLUSH, matchingCards) {
    init {
        require(matchingCards.size == 5) { "Matching cards be of size 5" }
        require(matchingCards.bySuit().size == 1) { "All cards must be of same suit" }
    }
}