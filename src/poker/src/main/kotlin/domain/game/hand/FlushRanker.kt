package domain.game.hand

import domain.game.deck.Card
import domain.game.deck.Suit
import domain.game.deck.bySuit
import domain.game.hand.HandRank.Opinion.FLUSH

class FlushRanker {
    fun rank(cards: Set<Card>): HandRank = when (val possibleFlushCards = flushCards(cards)) {
        emptyMap<Suit, List<Card>>() -> HandRank.NONE
        else -> HandRank(FLUSH, extractFlushCards(possibleFlushCards))
    }

    private fun extractFlushCards(possibleFlushCards: Map<Suit, List<Card>>) =
        possibleFlushCards.values.first().toSet()

    private fun flushCards(cards: Set<Card>) = cards.bySuit().filter { it.value.size >= 5 }
}

