package com.seanshubin.condorcet.prototype

fun main(args: Array<String>) {
    val lines = SampleDataGenerator.generateNames(10) + SampleDataGenerator.generateConfirmationNumbers(10)
    lines.forEach { println(it) }
    SampleDataGenerator.generateInputLines(5, 5, 5).forEach { println(it) }
}