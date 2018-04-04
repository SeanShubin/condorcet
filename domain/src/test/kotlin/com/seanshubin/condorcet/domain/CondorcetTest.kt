package com.seanshubin.condorcet.domain

import org.junit.Test
import kotlin.test.assertTrue

class CondorcetTest {
    val classLoader = this.javaClass.classLoader
    @Test
    fun testDataSamples() {
        runTest("01-spoiler")
        runTest("02-tactical-voting")
        runTest("03-schulze-example-from-wikipedia")
        runTest("04-vote-against")
        runTest("05-tie")
        runTest("06-random-data")
    }

    private fun runTest(name: String) {
        val inputLines = resourceNameToLines("test-data/$name/input.txt")
        val actualLines = Condorcet.processLines(inputLines)
        val expectedLines = resourceNameToLines("test-data/$name/expected.txt")
        assertLinesEqual(name, actualLines, expectedLines)
    }

    private fun assertLinesEqual(name: String, actualLines: List<String>, expectedLines: List<String>) {
        val result = ListDifference.diff(actualLines, expectedLines)
        if (!result.isSame) {
            println("difference in test $name")
            actualLines.forEach { println(it) }
        }
        assertTrue(result.isSame, result.messageLines.joinToString("\n"))
    }

    private fun resourceNameToLines(name: String): List<String> {
        val inputStream = classLoader.getResourceAsStream(name)
        if (inputStream == null) {
            throw RuntimeException("Unable to find resource named '$name'")
        } else {
            return IoUtil.inputStreamToLines(inputStream, GlobalConstants.CHARSET)
        }
    }
}