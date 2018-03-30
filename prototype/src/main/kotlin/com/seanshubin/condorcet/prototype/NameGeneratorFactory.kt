package com.seanshubin.condorcet.prototype

import com.seanshubin.condorcet.domain.NameGenerator
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.util.*

class NameGeneratorFactory(val random: Random, val charset: Charset) {
    fun createNameGenerator(): NameGenerator {
        val surnames = resourceNameToLines("surnames.txt").map { it.toLowerCase() }
        val femaleNames = resourceNameToLines("female-names.txt").map { it.toLowerCase() }
        val maleNames = resourceNameToLines("male-names.txt").map { it.toLowerCase() }
        val givenNames = femaleNames + maleNames
        return NameGenerator(random, givenNames, surnames)
    }

    fun resourceNameToLines(name: String): List<String> {
        val inputStream = getResourceAsStream(name)
        return inputStreamToLines(inputStream)
    }

    fun getResourceAsStream(name: String): InputStream {
        return this.javaClass.classLoader.getResourceAsStream(name)
                ?: throw RuntimeException("Resource named '$name' not found")
    }

    fun inputStreamToLines(inputStream: InputStream): List<String> {
        val reader = BufferedReader(InputStreamReader(inputStream, charset))
        try {
            return bufferedReaderToLines(reader)
        } finally {
            reader.close()
        }
    }

    fun bufferedReaderToLines(reader: BufferedReader): List<String> {
        val mutableList = mutableListOf<String>()
        var line = reader.readLine()
        while (line != null) {
            mutableList.add(line)
            line = reader.readLine()
        }
        return mutableList
    }
}