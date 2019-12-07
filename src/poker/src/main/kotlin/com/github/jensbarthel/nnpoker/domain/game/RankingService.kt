package com.github.jensbarthel.nnpoker.domain.game

import com.github.jensbarthel.nnpoker.domain.game.hand.CompoundRanker
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank
import com.github.jensbarthel.nnpoker.domain.game.hand.HoleCardPair
import org.springframework.stereotype.Service

@Service
class RankingService(private val compoundRanker: CompoundRanker) {
    fun rankHands(communityCards: CommunityCards, holeCards: Set<HoleCardPair>): Pair<HoleCardPair, HandRank> =
        holeCards
            .map { Pair(it, compoundRanker.rank(communityCards + it)) }
            .maxBy { it.second.opinion }
            ?: throw IllegalStateException()
}