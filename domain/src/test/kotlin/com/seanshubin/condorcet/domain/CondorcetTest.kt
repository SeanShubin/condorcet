package com.seanshubin.condorcet.domain

import org.junit.Test

class CondorcetTest {
    @Test
    fun testDataSamples() {
        runTest("01-spoiler")
        runTest("02-tactical-voting")
        runTest("03-schulze-example-from-wikipedia")
    }

    fun runTest(name: String): Unit {

//        assertLinesEqual(actualLines, expectedLines)
    }
}