package com.github.jensbarthel.nnpoker.domain.casino

import com.github.jensbarthel.nnpoker.domain.game.deck.Card
import com.github.jensbarthel.nnpoker.domain.game.deck.Deck
import com.github.jensbarthel.nnpoker.domain.game.deck.DeckFactory

class Table(tableNumber: TableNumber) {
    private val deckFactory = DeckFactory()

    private val deck: Deck

    val exposedCards = listOf<Card>()

    init {
        deck = deckFactory.create()
    }
}