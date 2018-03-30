package com.seanshubin.condorcet.prototype

import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class Runner(private val parser: Parser,
             private val charset: Charset) : Runnable {
    override fun run() {
        val baseDirectory = Paths.get("sample-data")
        val directories = baseDirectory.toFile().list().map { baseDirectory.resolve(Paths.get(it)) }
        directories.forEach { runExample(it) }
    }

    private fun runExample(directory: Path) {
        val conciseLines = readLines(directory.resolve("concise.txt"))
        val inputLines = parser.conciseToInput(conciseLines)
        inputLines.forEach { println(it) }

    }

    private fun readLines(path: Path): List<String> {
        return Files.readAllLines(path, charset)
    }
}
