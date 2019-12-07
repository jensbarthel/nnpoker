package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Card

data class HandRank(val opinion: Opinion, val matchingCards: Set<Card>) {
    enum class Opinion(val precedence: Int) {
        NONE(0),
        HIGH_CARD(1),
        PAIR(2),
        DOUBLE_PAIR(3),
        TRIPS(4),
        STRAIGHT(5),
        FLUSH(6),
        FULL_HOUSE(7),
        QUADS(8),
        STRAIGHT_FLUSH(9),
        ROYAL_FLUSH(10)
    }
    companion object {
        val NONE = HandRank(Opinion.NONE, emptySet())
    }
}