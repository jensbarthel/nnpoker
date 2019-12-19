package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Card
import com.github.jensbarthel.nnpoker.domain.game.deck.Face
import com.github.jensbarthel.nnpoker.domain.game.deck.byFace
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

    protected fun compareKickerTo(other: HandRank): Int =
        compareHighestCards(
            matchingCards.sortedDescending(),
            other.matchingCards.sortedDescending()
        )

    protected fun compareFaceAndKicker(face: Face, otherFace: Face, other: HandRank): Int =
        when (val faceComparison = face.compareTo(otherFace)) {
            0 -> compareKickerTo(other)
            else -> faceComparison
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

class BasicRank(opinion: Opinion, matchingCards: Set<Card>) : HandRank(opinion, matchingCards) {
    override fun compareTo(other: HandRank): Int = opinion.compareTo(other.opinion)
}

class HighCardRank(matchingCards: Set<Card>) : HandRank(Opinion.HIGH_CARD, matchingCards) {
    override fun compareTo(other: HandRank): Int = when (other) {
        is HighCardRank -> compareKickerTo(other)
        else -> opinion.compareTo(other.opinion)
    }
}

class PairRank(matchingCards: Set<Card>, private val pairFace: Face) : HandRank(PAIR, matchingCards) {
    init {
        require(matchingCards.byFace()[pairFace]?.size ?: 0 == 2) { "Matching cards must contain pair face" }
    }

    override fun compareTo(other: HandRank): Int = when (other) {
        is PairRank -> compareFaceAndKicker(pairFace, other.pairFace, other)
        else -> opinion.compareTo(other.opinion)
    }
}

