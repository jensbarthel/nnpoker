package domain.game.hand

import domain.game.hand.RankOpinion.*
import domain.game.deck.Card
import domain.game.deck.Suit
import domain.game.deck.bySuit

class FlushRanker {
    fun rank(cards: Set<Card>): HandRank = when (val possibleFlushCards = flushCards(cards)) {
        emptyMap<Suit, List<Card>>() -> HandRank.NONE
        else -> HandRank(FLUSH, extractFlushCards(possibleFlushCards))
    }

    private fun extractFlushCards(possibleFlushCards: Map<Suit, List<Card>>) =
        possibleFlushCards.values.first().toSet()

    private fun flushCards(cards: Set<Card>) = cards.bySuit().filter { it.value.size >= 5 }
}

data class HandRank(val opinion: RankOpinion, val matchingCards: Set<Card>) {
    companion object {
        val NONE = HandRank(RankOpinion.NONE, emptySet())
    }
}

enum class RankOpinion(val precedence: Int) {
    NONE(0),
    HIGH_CARD(1),
    PAIR(2),
    DOUBLE_PAIR(3),
    TRIPS(4),
    STRAIGHT(5),
    FLUSH(6),
    FULL_HOUSE(7),
    QUADS(8),
    STRAIGHT_FLUSH(9),
    ROYAL_FLUSH(10)
}