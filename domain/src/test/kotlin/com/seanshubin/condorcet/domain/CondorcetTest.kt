package com.seanshubin.condorcet.domain

import org.junit.Test
import kotlin.test.assertTrue

class CondorcetTest {
    val classLoader = ClassLoaderInstanceDelegate(this.javaClass.classLoader)
    @Test
    fun testDataSamples() {
        runTest("01-spoiler")
        runTest("02-tactical-voting")
        runTest("03-schulze-example-from-wikipedia")
    }

    private fun runTest(name: String) {
        val inputLines = resourceNameToLines("test-data/$name/input.txt")
        val actualLines = Condorcet.processLines(inputLines)
        val expectedLines = resourceNameToLines("test-data/$name/expected.txt")
        assertLinesEqual(actualLines, expectedLines)
    }

    private fun assertLinesEqual(actualLines: List<String>, expectedLines: List<String>) {
        val result = ListDifference.diff(actualLines, expectedLines)
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