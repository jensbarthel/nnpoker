package domain.game

import domain.game.deck.Card

class Ranker {
    fun evaluate(cards: Set<Card>) {
        require(cards.size == 7)

    }
}

