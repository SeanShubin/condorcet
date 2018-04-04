package com.seanshubin.condorcet.console

import com.seanshubin.condorcet.domain.Condorcet
import com.seanshubin.condorcet.domain.GlobalConstants
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

fun main(args: Array<String>) {
    val inputLines = readAllLines(System.`in`)
    val outputLines = Condorcet.processLines(inputLines)
    outputLines.forEach { println(it) }
}

fun readAllLines(inputStream: InputStream): List<String> =
        readAllLines(BufferedReader(InputStreamReader(
                inputStream, GlobalConstants.CHARSET)))

fun readAllLines(reader: BufferedReader): List<String> {
    val lines = mutableListOf<String>()
    var line = reader.readLine()
    while (line != null) {
        lines.add(line)
        line = reader.readLine()
    }
    return lines
}