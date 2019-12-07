package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Card
import com.github.jensbarthel.nnpoker.domain.game.deck.Suit
import com.github.jensbarthel.nnpoker.domain.game.deck.bySuit
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.FLUSH

class FlushRanker : HandRanker {
    override fun rank(cards: Set<Card>): HandRank = when (val possibleFlushCards = flushCards(cards)) {
        emptyMap<Suit, List<Card>>() -> HandRank.NONE
        else -> HandRank(FLUSH, extractFlushCards(possibleFlushCards))
    }

    private fun extractFlushCards(possibleFlushCards: Map<Suit, List<Card>>) =
        possibleFlushCards.values.first().toSet()

    private fun flushCards(cards: Set<Card>) = cards.bySuit().filter { it.value.size >= 5 }
}

