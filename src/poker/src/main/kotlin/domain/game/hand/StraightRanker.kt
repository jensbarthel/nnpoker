package domain.game.hand

import domain.game.deck.Card
import domain.game.deck.Rank
import domain.game.deck.Rank.*
import domain.game.deck.byRank
import domain.game.hand.HandRank.Companion.NONE
import domain.game.hand.HandRank.Opinion
import domain.game.hand.HandRank.Opinion.*

class StraightRanker : HandRanker {
    override fun rank(cards: Set<Card>): HandRank {
        if (cards.isEmpty()) {
            return NONE
        }

        val cardsByRank = cards.byRank()
        val sortedStraightRanks = cardsByRank.keys.map { it.straightRank() }.sorted()
        val adjustedStraightRanks = handleAceForWheel(sortedStraightRanks)
        val sortedStraights = splitIntoSequences(adjustedStraightRanks).filter { it.size >= 5 }
        return if (sortedStraights.any()) {

            val straightCards =
                sortedStraights
                    .last()
                    .takeLast(5)
                    .map { it.toRank() }
                    .map { (cardsByRank[it] ?: error("Illegal state")).first() }
                    .toSet()
            HandRank(STRAIGHT, straightCards)
        } else {
            NONE
        }
    }

    private fun splitIntoSequences(cardsToRank: List<Int>): List<MutableList<Int>> {
        var currentSequence = mutableListOf(cardsToRank[0])
        val rankSequences = mutableListOf(currentSequence)
        for ((index, currentRankPrecedence) in cardsToRank.withIndex().drop(1)) {
            if (cardsToRank[index - 1] == currentRankPrecedence - 1) {
                currentSequence.add(currentRankPrecedence)
            } else {
                currentSequence = mutableListOf(currentRankPrecedence)
                rankSequences.add(currentSequence)
            }
        }
        return rankSequences
    }

    private fun handleAceForWheel(sorted: List<Int>) = if (sorted.contains(ACE.straightRank())) {
        sorted + 13
    } else {
        sorted
    }

    // fun toCon

    private fun Rank.straightRank() = when (this) {
        ACE -> 0
        TWO -> 1
        THREE -> 2
        FOUR -> 3
        FIVE -> 4
        SIX -> 5
        SEVEN -> 6
        EIGHT -> 7
        NINE -> 8
        TEN -> 9
        JACK -> 10
        QUEEN -> 11
        KING -> 12
    }

    private fun Int.toRank() = when (this) {
        0, 13 -> ACE
        1 -> TWO
        2 -> THREE
        3 -> FOUR
        4 -> FIVE
        5 -> SIX
        6 -> SEVEN
        7 -> EIGHT
        8 -> NINE
        9 -> TEN
        10 -> JACK
        11 -> QUEEN
        12 -> KING
        else -> throw IllegalArgumentException("Unknown Rank precedence: $this. Can convert 0-13")
    }
}