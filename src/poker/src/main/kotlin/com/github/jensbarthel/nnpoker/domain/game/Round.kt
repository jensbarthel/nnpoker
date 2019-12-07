package com.github.jensbarthel.nnpoker.domain.game

class Round {

    private var state: State = State.PREFLOP
    private var pot = Pot()
}