package com.github.jensbarthel.nnpoker.domain.game

import com.github.jensbarthel.nnpoker.domain.game.deck.Card
import com.github.jensbarthel.nnpoker.domain.game.hand.HoleCardPair

class CommunityCards(val cards: Set<Card>) {
    init {
        require(cards.size <= 5)
        require(cards.size >= 3)
    }

    operator fun plus(card: Card) = CommunityCards(cards + card)
    operator fun plus(holeCardPair: HoleCardPair) = cards + holeCardPair.first + holeCardPair.second
}