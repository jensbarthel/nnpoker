package com.github.jensbarthel.nnpoker.domain.casino

import com.github.jensbarthel.nnpoker.domain.game.deck.Card

interface BettingParty {
    fun receiveCards(first: Card, second: Card)
    fun bet(amount: Int)
}
