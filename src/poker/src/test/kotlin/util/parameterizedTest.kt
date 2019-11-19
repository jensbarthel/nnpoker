package util

import org.junit.jupiter.api.DynamicTest

fun <T> parameterizedTest(vararg values: T, test: (T) -> Unit): List<DynamicTest> {
    return values.map { value ->
        DynamicTest.dynamicTest("'$value'") { test(value) }
    }
}