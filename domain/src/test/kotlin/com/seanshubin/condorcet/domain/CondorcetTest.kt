package com.seanshubin.condorcet.domain

import org.junit.Test
import kotlin.test.assertTrue

class CondorcetTest {
    @Test
    fun testDataSamples() {
        runTest("01-spoiler")
        runTest("02-tactical-voting")
        runTest("03-schulze-example-from-wikipedia")
    }

    private fun runTest(name: String) {
        val actualLines: List<String> = listOf("a", "b", "c")
        val expectedLines: List<String> = listOf("a", "d", "c")
        assertLinesEqual(actualLines, expectedLines)
    }

    private fun assertLinesEqual(actualLines: List<String>, expectedLines: List<String>) {
        val result = ListDifference.diff(actualLines, expectedLines)
        assertTrue(result.isSame, result.messageLines.joinToString("\n"))
    }
}