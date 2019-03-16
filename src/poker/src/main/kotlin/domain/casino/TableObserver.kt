package domain.casino

import domain.game.deck.Card

interface TableObserver {
    fun updateCommunityCards(vararg cards: Card)
}
