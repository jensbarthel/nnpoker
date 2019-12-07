package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Card
import com.github.jensbarthel.nnpoker.domain.game.hand.HandRank.Opinion.HIGH_CARD

class HighCardRanker : HandRanker{
    override fun rank(cards: Set<Card>): HandRank {
        return HandRank(HIGH_CARD, cards.sortedDescending().take(5).toSet())
    }
}