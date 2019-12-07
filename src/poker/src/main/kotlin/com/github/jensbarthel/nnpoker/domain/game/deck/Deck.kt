package com.github.jensbarthel.nnpoker.domain.game.deck

class Deck(cards: Set<Card>) {
    private val cardsInDeck = cards.shuffled().toMutableList()
    val size get() = cardsInDeck.size

    init {
        require(cards.size == 52)
    }

    private fun draw() = cardsInDeck.removeAt(0)
    fun draw(amount: Int) = (0 until amount).map { draw() }
}
