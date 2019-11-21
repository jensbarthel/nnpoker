package domain.game.deck

data class Card(val suit: Suit, val face: Face) : Comparable<Card> {
    override fun compareTo(other: Card): Int {
        return face.compareTo(other.face)
    }

    override fun toString(): String = "$face of $suit"
}

fun Collection<Card>.bySuit() = this.groupBy { it.suit }
fun Collection<Card>.byFace() = this.groupBy { it.face }

infix fun Face.of(suit: Suit) = Card(suit, this)