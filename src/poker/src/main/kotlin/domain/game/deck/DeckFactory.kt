package domain.game.deck

class DeckFactory {
    fun create(): Deck =
        Deck(
            Suit.values()
                .map { it to Face.values() }
                .flatMap { suitToFaces -> suitToFaces.second.map { suitToFaces.first to it } }
                .map {
                    Card(it.first, it.second)
                }.toSet()
        )
}