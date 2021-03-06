package com.seanshubin.condorcet.prototype

import com.seanshubin.condorcet.domain.Condorcet
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    val seed = 12345L
    val howManyCandidates = 100
    val howManyVoters = 10000
    val howManyBallots = 9000
    val inputLines = SampleDataGenerator(seed).generateInputLines(howManyCandidates, howManyVoters, howManyBallots)
    val outputDir = Paths.get("domain", "src", "test", "resources", "test-data", "10-large-data-set")
    Files.createDirectories(outputDir)
    val inputFile = outputDir.resolve("input.txt")
    PrintWriter(Files.newBufferedWriter(inputFile)).use { out ->
        inputLines.forEach { out.println(it) }
    }
    val outputLines = Condorcet.processLines(inputLines)
    val expectedFile = outputDir.resolve("expected.txt")
    PrintWriter(Files.newBufferedWriter(expectedFile)).use { out ->
        outputLines.forEach { out.println(it) }
    }
    println("java -jar console/target/condorcet.jar < $inputFile")
}