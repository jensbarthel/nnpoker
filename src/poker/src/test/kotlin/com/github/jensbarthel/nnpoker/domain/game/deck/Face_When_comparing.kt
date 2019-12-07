package com.github.jensbarthel.nnpoker.domain.game.deck

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Face_When_comparing {

    companion object {
        @JvmStatic
        private fun orderedParamters() = Stream.of(
            Arguments.of(Face.TWO, Face.THREE),
            Arguments.of(Face.THREE, Face.FOUR),
            Arguments.of(Face.FOUR, Face.FIVE),
            Arguments.of(Face.FIVE, Face.SIX),
            Arguments.of(Face.SIX, Face.SEVEN),
            Arguments.of(Face.SEVEN, Face.EIGHT),
            Arguments.of(Face.EIGHT, Face.NINE),
            Arguments.of(Face.NINE, Face.TEN),
            Arguments.of(Face.TEN, Face.JACK),
            Arguments.of(Face.JACK, Face.QUEEN),
            Arguments.of(Face.QUEEN, Face.KING),
            Arguments.of(Face.KING, Face.ACE)
        )
    }

    @ParameterizedTest
    @MethodSource("orderedParamters")
    fun `Then values are in order`(smallerFace: Face, greaterFace: Face) {
        // Assert
        Assertions.assertTrue { smallerFace < greaterFace }
    }
}