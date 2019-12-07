package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Card

interface HandRanker {
    fun rank(cards: Set<Card>): HandRank
}