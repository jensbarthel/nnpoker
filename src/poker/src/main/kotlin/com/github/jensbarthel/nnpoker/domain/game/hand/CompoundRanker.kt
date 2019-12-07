package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Card
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Companion.NONE
import org.springframework.stereotype.Component

@Component
class CompoundRanker(
    private val straightFlushRanker: StraightFlushRanker,
    private val flushRanker: StraightFlushRanker,
    private val pairingRanker: PairingRanker,
    private val straightRanker: StraightRanker,
    private val highCardRanker: HighCardRanker
) {
    fun rank(cards: Set<Card>): HandRank {
        val highestRank = setOf(
            straightFlushRanker,
            flushRanker,
            pairingRanker,
            straightRanker,
            highCardRanker
        ).map { it.rank(cards) }.maxBy { it.opinion }!!

        require(highestRank != NONE) { "Hand rank must be valid" }
        return highestRank
    }
}