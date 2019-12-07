package com.github.jensbarthel.nnpoker.domain.casino

import com.github.jensbarthel.nnpoker.domain.game.deck.Card

interface TableObserver {
    fun updateCommunityCards(vararg cards: Card)
}
