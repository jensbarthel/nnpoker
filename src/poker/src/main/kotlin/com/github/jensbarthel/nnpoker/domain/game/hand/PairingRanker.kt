package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Card
import com.github.jensbarthel.nnpoker.domain.game.deck.Face
import com.github.jensbarthel.nnpoker.domain.game.deck.byFace
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Companion.NONE
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.*
import org.springframework.stereotype.Component

@Component
class PairingRanker : HandRanker {
    override fun rank(cards: Set<Card>): HandRank {
        val cardsByFace = cards.byFace().toSortedMap(Comparator.reverseOrder())
        val quads = cardsByFace.filter { it.value.size == 4 }
        val trips = cardsByFace.filter { it.value.size == 3 }
        val pairs = cardsByFace.filter { it.value.size == 2 }
        return when {
            quads.any() -> BasicRank(QUADS, fillWithHighestCards(quads.values.first(), cards))
            trips.any() -> tripsOrFullHouse(pairs, trips, cards)
            pairs.any() -> pairOrDoublePair(pairs, cards)
            else -> NONE
        }
    }

    private fun pairOrDoublePair(pairs: Map<Face, List<Card>>, cards: Set<Card>): HandRank = when(pairs.size) {
        0 -> NONE
        1 -> PairRank(fillWithHighestCards(pairs.values.first(), cards), pairs.keys.first())
        else -> BasicRank(DOUBLE_PAIR, fillWithHighestCards(pairs.values.take(2).flatten(), cards))
    }

    private fun tripsOrFullHouse(
        pairs: Map<Face, List<Card>>,
        trips: Map<Face, List<Card>>,
        cards: Set<Card>
    ): HandRank {
        require(!trips.values.any { it.size != 3})
        require(!pairs.values.any { it.size != 2})

        val tripsCards = trips.values.first()
        return if (pairs.any()) {
            val fullHousePair = pairs.values.first()
            BasicRank(FULL_HOUSE, (tripsCards + fullHousePair).toSet())
        } else {
            TripsRank(fillWithHighestCards(tripsCards, cards), trips.keys.first())
        }
    }

    private fun fillWithHighestCards(rankedCards: List<Card>, allCards: Set<Card>): Set<Card> {
        require(rankedCards.size < 5) { "Can only rank quads, full house, trips or pairs" }
        return (rankedCards + (allCards - rankedCards).sortedDescending().take(5 - rankedCards.size)).toSet()
    }
}
