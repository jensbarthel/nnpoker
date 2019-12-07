package com.github.jensbarthel.nnpoker.domain.game.hand

import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.TestFactory
import util.parameterizedTest

class PairRank_When_comparing {
    @TestFactory
    fun `If opinions are different Then they are compared`() = parameterizedTest(
        Pair(HandRank.Opinion.HIGH_CARD, 1),
        Pair(HandRank.Opinion.DOUBLE_PAIR, -1)
    ) { (otherOpinion, expectedComparison) ->
        // Arrange
        val otherRank = mockk<HandRank>().also { every { it.opinion } returns otherOpinion }

        // Act
        val comparison = PairRank(mockk(), mockk()).compareTo(otherRank)

        // Assert
        comparison `should be equal to` expectedComparison
    }
}