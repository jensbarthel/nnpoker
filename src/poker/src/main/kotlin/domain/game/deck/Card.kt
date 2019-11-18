package domain.game.deck

data class Card(val suit: Suit, val rank: Rank) : Comparable<Card> {
    override fun compareTo(other: Card): Int {
        return this.rank.compareTo(other.rank)
    }

    override fun toString(): String = "$rank of $suit"
}

fun Collection<Card>.bySuit() = this.groupBy { it.suit }
fun Collection<Card>.byRank() = this.groupBy { it.rank }

infix fun Rank.of(suit: Suit) = Card(suit, this)