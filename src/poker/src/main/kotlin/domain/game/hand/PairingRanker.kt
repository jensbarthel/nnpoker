package domain.game.hand

import domain.game.deck.Card
import domain.game.deck.Rank
import domain.game.deck.byRank
import domain.game.hand.HandRank.Companion.NONE
import domain.game.hand.HandRank.Opinion.*

class PairingRanker : HandRanker {
    override fun rank(cards: Set<Card>): HandRank {
        val cardsByRank = cards.byRank().toSortedMap(Comparator.reverseOrder())
        val quads = cardsByRank.filter { it.value.size == 4 }
        val trips = cardsByRank.filter { it.value.size == 3 }
        val pairs = cardsByRank.filter { it.value.size == 2 }
        return when {
            quads.any() -> HandRank(QUADS, fillWithHighestCards(quads.values.first(), cards))
            trips.any() -> tripsOrFullHouse(pairs, trips, cards)
            pairs.any() -> pairOrDoublePair(pairs, cards)
            else -> NONE
        }
    }

    private fun pairOrDoublePair(pairs: Map<Rank, List<Card>>, cards: Set<Card>): HandRank = when(pairs.size) {
        0 -> NONE
        1 -> HandRank(PAIR, fillWithHighestCards(pairs.values.first(), cards))
        else -> HandRank(DOUBLE_PAIR, fillWithHighestCards(pairs.values.take(2).flatten(), cards))
    }

    private fun tripsOrFullHouse(
        pairs: Map<Rank, List<Card>>,
        trips: Map<Rank, List<Card>>,
        cards: Set<Card>
    ): HandRank {
        val pairsWithoutTrips = pairs.minus(trips.keys)
        val tripsCards = trips.values.first()
        return if (pairsWithoutTrips.any()) {
            val fullHousePair = pairsWithoutTrips.values.first()
            HandRank(FULL_HOUSE, (tripsCards + fullHousePair).toSet())
        } else {
            HandRank(TRIPS, fillWithHighestCards(tripsCards, cards))
        }
    }

    private fun fillWithHighestCards(rankedCards: List<Card>, allCards: Set<Card>): Set<Card> {
        require(rankedCards.size < 5) { "Can only rank quads, full house, trips or pairs" }
        return (rankedCards + (allCards - rankedCards).sortedDescending().take(5 - rankedCards.size)).toSet()
    }
}
