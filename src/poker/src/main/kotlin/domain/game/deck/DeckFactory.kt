package domain.game.deck

class DeckFactory {
    fun create(): Deck =
        Deck(
            Suit.values()
                .map { it to Rank.values() }
                .flatMap { suitToRanks -> suitToRanks.second.map { suitToRanks.first to it } }
                .map {
                    Card(it.first, it.second)
                }.toSet()
        )
}