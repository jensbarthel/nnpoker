package com.github.jensbarthel.nnpoker.domain.game.hand

import com.github.jensbarthel.nnpoker.domain.game.deck.Card

data class HandRank(val opinion: Opinion, val matchingCards: Set<Card>) {
    enum class Opinion {
        NONE,
        HIGH_CARD,
        PAIR,
        DOUBLE_PAIR,
        TRIPS,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        QUADS,
        STRAIGHT_FLUSH,
        ROYAL_FLUSH
    }

    companion object {
        val NONE = HandRank(Opinion.NONE, emptySet())
    }
}