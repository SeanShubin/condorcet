package com.seanshubin.condorcet.prototype

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*

fun main(args: Array<String>) {
    class GetClassLoaderFromMe

    val classLoader: ClassLoader = GetClassLoaderFromMe().javaClass.classLoader
    val charset = StandardCharsets.UTF_8

    fun getResourceAsStream(name: String): InputStream {
        return classLoader.getResourceAsStream(name) ?: throw RuntimeException("Resource named '$name' not found")
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

    fun inputStreamToLines(inputStream: InputStream): List<String> {
        val reader = BufferedReader(InputStreamReader(inputStream, charset))
        try {
            return bufferedReaderToLines(reader)
        } finally {
            reader.close()
        }
    }

    fun resourceNameToLines(name: String): List<String> {
        val inputStream = getResourceAsStream(name)
        return inputStreamToLines(inputStream)
    }

    val surnames = resourceNameToLines("surnames.txt").map { it.toLowerCase() }
    val femaleNames = resourceNameToLines("female-names.txt").map { it.toLowerCase() }
    val maleNames = resourceNameToLines("male-names.txt").map { it.toLowerCase() }
    val givenNames = femaleNames + maleNames
    val seed = 12345L
    val random = Random(seed)

    fun chooseRandom(choices: List<String>): String = choices.get(random.nextInt(choices.size))

    fun randomName(): String {
        val givenName = chooseRandom(givenNames)
        val surname = chooseRandom(surnames)
        return "$givenName $surname"
    }

    (1..10).map { randomName() }.forEach { println(it) }
}
/*
eligible voters
...
name confirm timestamp 1 a 2 b 2 c 3 d

-----------------

voted
...
did not vote
...
votes
confirm 1 a 2 b 2 c 3 d
tally
1st a
2nd b c
4th d
 */
