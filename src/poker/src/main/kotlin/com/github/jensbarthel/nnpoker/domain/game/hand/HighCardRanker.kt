package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Card
import org.springframework.stereotype.Component

@Component
class HighCardRanker : HandRanker{
    override fun rank(cards: Set<Card>): HandRank {
        val highestCards = cards.sortedDescending().take(5)
        return HighCardRank(highestCards.toSet())
    }
}