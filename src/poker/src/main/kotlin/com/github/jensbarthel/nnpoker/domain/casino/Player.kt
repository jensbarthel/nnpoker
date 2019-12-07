package com.github.jensbarthel.nnpoker.domain.casino

import com.github.jensbarthel.nnpoker.domain.game.deck.Card

class Player : TableObserver, BettingParty {
    override fun updateCommunityCards(vararg cards: Card) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun bet(amount: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun receiveCards(first: Card, second: Card) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val funds = 0

    fun join(table: Table) {

    }
}