package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Card
import com.github.jensbarthel.nnpoker.domain.game.deck.Face
import com.github.jensbarthel.nnpoker.domain.game.deck.byFace
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.PAIR
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.TRIPS

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

class HighCardRank(matchingCards: Set<Card>) : HandRank(Opinion.HIGH_CARD, matchingCards) {
    override fun compareTo(other: HandRank): Int = when (other) {
        is HighCardRank -> compareKickerTo(other)
        else -> super.compareTo(other)
    }
}

class PairRank(matchingCards: Set<Card>, pairFace: Face) : HandRank(PAIR, matchingCards) {
    init {
        require(matchingCards.byFace()[pairFace]?.size ?: 0 == 2) { "Matching cards must contain pair face" }
    }

    override fun compareTo(other: HandRank): Int = when (other) {
        is PairRank -> compareKickerTo(other)
        else -> super.compareTo(other)
    }
}

class TripsRank(matchingCards: Set<Card>, tripsFace: Face) : HandRank(TRIPS, matchingCards) {
    init {
        require(matchingCards.byFace()[tripsFace]?.size ?: 0 == 3) { "Matching cards must contain pair face" }
    }

    override fun compareTo(other: HandRank): Int = when (other) {
        is TripsRank -> compareKickerTo(other)
        else -> super.compareTo(other)
    }
}