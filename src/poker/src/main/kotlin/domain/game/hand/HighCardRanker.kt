package domain.game.hand

import domain.game.deck.Card
import domain.game.hand.HandRank.Opinion.HIGH_CARD

class HighCardRanker : HandRanker{
    override fun rank(cards: Set<Card>): HandRank {
        return HandRank(HIGH_CARD, cards.sortedDescending().take(5).toSet())
    }
}