package domain.game.hand

import domain.game.hand.RankDescription.*
import domain.game.deck.Card
import domain.game.deck.bySuit

class Ranker {
    fun rank(cards: Set<Card>): RankDescription {
        require(cards.size >= 5)
        if (isFlush(cards)) {
            return FLUSH
        }
        return HIGH_CARD
    }

    private fun isFlush(cards: Set<Card>): Boolean = cards.bySuit().filter { it.value.size >= 5 }.any()
}

enum class RankDescription(val precedence: Int) {
    HIGH_CARD(0),
    PAIR(1),
    DOUBLE_PAIR(2),
    TRIPS(3),
    STRAIGHT(4),
    FLUSH(5),
    FULL_HOUSE(6),
    QUADS(7),
    STRAIGHT_FLUSH(8),
    ROYAL_FLUSH(9)
}

interface Rank {
    val description: RankDescription
    fun compareTo(rank: Rank): Int
}