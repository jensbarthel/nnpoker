package domain.game.deck

data class Card(val suit: Suit, val rank: Rank) : Comparable<Card> {
    override fun compareTo(other: Card): Int {
        return this.rank.compareTo(other.rank)
    }
}