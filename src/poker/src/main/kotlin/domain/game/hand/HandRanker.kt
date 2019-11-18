package domain.game.hand

import domain.game.deck.Card

interface HandRanker {
    fun rank(cards: Set<Card>): HandRank
}