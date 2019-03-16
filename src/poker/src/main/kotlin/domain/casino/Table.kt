package domain.casino

import domain.game.deck.Card
import domain.game.deck.Deck
import domain.game.deck.DeckFactory

class Table(tableNumber: TableNumber) {
    private val deckFactory = DeckFactory()

    private val deck: Deck

    val exposedCards = listOf<Card>()

    init {
        deck = deckFactory.create()
    }
}