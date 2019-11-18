package domain.game.hand

import domain.game.deck.Card
import domain.game.deck.Rank

class Hand(private val cards: Set<Card>) {

    init {
        require(cards.size >= 2)
    }

    fun isStraight(): Boolean {
        if (cards.size < 5) {
            return false
        }

        val ranks = cards.map { it.rank }.sorted().toMutableList()
        val distances = ranks.zipWithNext().map { Distance(it.second, it.first) }
        var matchingDistanceIdxs = mutableListOf<Int>()

        loop@ for (i in 0 until distances.size) {
            val distance = distances[i]
            when(distance) {
                0 -> continue@loop
                1 -> {
                    matchingDistanceIdxs.add(i)
                    if(matchingDistanceIdxs.size == 4)
                        return true
                }
                else -> matchingDistanceIdxs.clear()
            }
        }

        var straightIdxs = mutableListOf<Int>()
        if (ranks.last() == Rank.ACE && ranks.first() == Rank.TWO) {
            straightIdxs.add(ranks.size - 1)
            var previousRank = ranks.last()
            for (i in 0 until ranks.size) {
                var rank = ranks[i]
                if (rank > Rank.FIVE) {
                    break
                }
                if (Distance(rank, previousRank) > 1) {
                    straightIdxs.clear()
                } else {
                    straightIdxs.add(i)
                    if (straightIdxs.size == 5) {
                        return true
                    }
                }
                previousRank = rank
            }

        }
        return false
    }

    private fun Distance(leftRank: Rank, rightRank: Rank) =
        leftRank.ordinal - rightRank.ordinal % Rank.values().size

    fun isFlush(): Boolean = cards.size >= 5 && cards.map { it.suit }.toSet().size == 1
}