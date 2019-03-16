package domain.casino

import domain.game.deck.Card

interface BettingParty {
    fun receiveCards(first: Card, second: Card)
    fun bet(amount: Int)
}
